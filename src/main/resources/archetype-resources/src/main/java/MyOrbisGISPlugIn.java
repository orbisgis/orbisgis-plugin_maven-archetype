package ${package};

import org.orbisgis.core.ui.pluginSystem.AbstractPlugIn;
import org.orbisgis.core.ui.pluginSystem.PlugInContext;
import org.orbisgis.core.ui.pluginSystem.workbench.Names;

public class MyOrbisGISPlugIn extends AbstractPlugIn {

        @Override
        public void initialize(PlugInContext context) throws Exception {

                context.getFeatureInstaller().addMainMenuItem(this,
                        new String[]{Names.FILE, "My menu entry"},
                        "Run my plugin", false,
                        null, null, null, context);
        }

        @Override
        public boolean execute(PlugInContext context) throws Exception {
                System.out.println("You ran my plugin!");
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }
}
