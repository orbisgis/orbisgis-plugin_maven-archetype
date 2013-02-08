package ${package};

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.gdms.sql.function.Function;

/**
 * Registers services provided by this plugin bundle.
 */
public class Activator implements BundleActivator {
        /**
         * Starting bundle, register sql functions.
         * @param bc
         * @throws Exception
         */
        @Override
        public void start(BundleContext bc) throws Exception {
                bc.registerService(Function.class,
                        new Log10(),
                        null);

        }

        /**
         * Called before the bundle is unloaded.
         * @param bc
         * @throws Exception
         */
        @Override
        public void stop(BundleContext bc) throws Exception {

        }
}
