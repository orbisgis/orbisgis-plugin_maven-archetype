package ${package};

import org.apache.commons.io.IOUtils;
import org.orbisgis.wpsservice.LocalWpsService;

import org.orbisgis.wpsservice.controller.process.ProcessIdentifier;
import org.orbisgis.wpsclient.WpsClient;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URI;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Main class of the plugin which declares the scripts to add, their locations in the process tree and the icons
 * associated.
 * In the WpsService, the script are organized in a tree, which has the WpsService as root.
 * When the plugin is launched , the 'activate()' method is call. This method load the scripts in the
 * WpsService and refresh the WpsClient.
 * When the plugin is stopped or uninstalled, the 'deactivate()' method is called. This method removes the loaded script
 * from the WpsService and refreshes the WpsClient.
 *
 */
@Component(immediate=true)
public class WpsScriptsPackage {
    /** {@link I18n} object */
    private static final I18n I18N = I18nFactory.getI18n(WpsScriptPlugin.class);
    /** {@link Logger} */
    private static final Logger LOGGER = LoggerFactory.getLogger(WpsScriptPlugin.class);

    /** String parameters for the plugin. */
    /** Resource path to the folder containing the scripts. */
    private static final String SCRIPTS_RESOURCE_FOLDER_PATH = "scripts";
    /** Resource path to the folder containing the icons. */
    private static final String ICONS_RESOURCE_FOLDER_PATH = "icons";
    /** Name of the icon file to use. */
    private static final String ICON_NAME = "icon.png";
    /** Groovy extension. */
    private static final String GROOVY_EXTENSION = ".groovy";
    /** Base path of the script. */
    private static final String BASE_PATH = "Scripts";

    /** Icon {@link URL} array transmitted as process {@link ProcessMetadata.INTERNAL_METADATA INTERNAL_METADATA}. */
    private URL[] icons = new URL[]{};
    /** Cached map of the script path, the script {@link URL} as key, the path as value. This map is build when the
     *  method {@link #getScriptsList()} is called.*/
    private Map<URL, String> cachedPath;

    @Activate
    public void activate(){
        //Initialize icons and path collections
        icons = new URL[]{this.getClass().getResource(ICONS_RESOURCE_FOLDER_PATH+"/"+ICON_NAME)};
        cachedPath = new HashMap<>();

        I18N.marktr("Scripts");
    }

    @Deactivate
    public void deactivate(){
        //Nothing to do
    }

    @Override
    public Map<String, Object> getGroovyProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("i18n",I18N);
        return properties;
    }

    @Override
    public List<URL> getScriptsList() {
        File f = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        URL url = this.getClass().getResource(SCRIPTS_RESOURCE_FOLDER_PATH);
        if(!url.toString().startsWith("bundle")){
            try {
                return getAllSubJarUrl(new JarFile(f), "org/orbisgis/orbiswps/scripts/scripts");
            } catch (IOException e) {
                LOGGER.error(I18N.tr("Unable to read the scripts inside the jar :\n{0}", e.getMessage()));
            }
        }
        return getAllSubUrl(this.getClass().getResource(SCRIPTS_RESOURCE_FOLDER_PATH));
    }

    @Override
    public Map<ProcessMetadata.INTERNAL_METADATA, Object> getScriptMetadata(URL scriptUrl) {
        Map<ProcessMetadata.INTERNAL_METADATA, Object> map = new HashMap<>();
        map.put(ProcessMetadata.INTERNAL_METADATA.IS_REMOVABLE, false);
        map.put(ProcessMetadata.INTERNAL_METADATA.NODE_PATH, cachedPath.get(scriptUrl));
        map.put(ProcessMetadata.INTERNAL_METADATA.ICON_ARRAY, icons);
        return map;
    }

    @Override
    public I18n getI18n() {
        return I18nFactory.getI18n(WpsScriptPlugin.class);
    }

    /**
     * Return the list of the {@link URL} of the Groovy files inside the given root url. It also puts in cache all the
     * path associated to the script url.
     * @param url Root {@link URL} to explore to return the groovy files.
     * @return List of {@link URL} of the groovy files.
     */
    private List<URL> getAllSubUrl(URL url){
        List<URL> list = new ArrayList<>();
        //Iterates over all the file and folder URL contained in the root URL
        for(URL u : getChildUrl(url)){
            //If the URL is a groovy file, build the node path, cache it and add the url to the list to return.
            if(u.getFile().endsWith(GROOVY_EXTENSION)){
                int pathStartIndex = this.getClass().getResource(SCRIPTS_RESOURCE_FOLDER_PATH).toString().length();
                String[] pathArray = new File(u.toString()).getParent().substring(pathStartIndex).split("/");
                StringBuilder finalPath = new StringBuilder(I18N.tr(BASE_PATH));
                for(String pathPart : pathArray){
                    finalPath.append("/").append(I18N.tr(pathPart));
                }
                cachedPath.put(u, finalPath.toString());
                list.add(u);
            }
            //Else, explore the URL
            else{
                list.addAll(getAllSubUrl(u));
            }
        }
        return list;
    }

    /**
     * Return the list of the {@link URL} of the Groovy files inside the given root url. It also puts in cache all the
     * path associated to the script url.
     * @return List of {@link URL} of the groovy files.
     */
    private List<URL> getAllSubJarUrl(JarFile jarFile, String path){
        List<URL> list = new ArrayList<>();
        //Iterates over all the file and folder URL contained in the root URL
        final Enumeration<JarEntry> entries = jarFile.entries(); //gives ALL entries in jar
        while(entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().startsWith(path + "/") && entry.getName().endsWith(".groovy")) { //filter according to the path
                try {
                    File file = File.createTempFile("orbisgis", ".groovy");
                    Writer writer = new FileWriter(file);
                    IOUtils.copy(jarFile.getInputStream(jarFile.getEntry(entry.getName())), writer);
                    writer.flush();
                    writer.close();
                    list.add(file.toURI().toURL());
                } catch (IOException e) {
                    LOGGER.error(I18N.tr("Unable to copy the script {0} from the jar file.", entry.getName()));
                }
            }
        }
        return list;
    }

    /**
     * Returns the list of child {@link URL} of a root {@link URL} no matter if the file are located in an OSG bundle.
     * @param url Root {@link URL}.
     * @return List of child {@link URL}.
     */
    private List<URL> getChildUrl(URL url){
        List<URL> childUrl = new ArrayList<>();

        //Case of an osgi bundle
        Bundle bundle = FrameworkUtil.getBundle(this.getClass());
        if(bundle != null) {
            Enumeration<URL> enumUrl = bundle.findEntries(url.getFile(), "*", false);
            while(enumUrl.hasMoreElements()) {
                childUrl.add(enumUrl.nextElement());
            }
            return childUrl;
        }

        //Other case
        try {
            File f = new File(url.toURI());
            if(f.exists()){
                for(File child : f.listFiles()){
                    childUrl.add(child.toURI().toURL());
                }
                return childUrl;
            }
        } catch (URISyntaxException|MalformedURLException ignored) {}

        //Unknown case, return empty list
        LOGGER.error(I18N.tr("Unable to explore the URL {0}", url));
        return new ArrayList<>();
    }
}
