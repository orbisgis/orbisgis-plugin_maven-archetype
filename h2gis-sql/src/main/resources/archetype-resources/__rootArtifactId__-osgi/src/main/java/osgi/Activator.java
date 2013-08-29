#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.osgi;

import ${package}.core.DummyFunction;
import org.h2gis.h2spatialapi.Function;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Registers services provided by this plugin bundle.
 */
public class Activator implements BundleActivator {
        /**
         * Starting bundle, register services.
         * @param bc
         * @throws Exception
         */
        @Override
        public void start(BundleContext bc) throws Exception {
            bc.registerService(Function.class,
                    new DummyFunction(),
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
