package ${package};

import org.orbisgis.core.ui.pluginSystem.Extension;
import org.orbisgis.core.ui.pluginSystem.PlugInContext;

public class MyExtension extends Extension {

        @Override
        public void configure(PlugInContext context) throws Exception {
                new MyPlugIn().initialize(context);
        }
}
