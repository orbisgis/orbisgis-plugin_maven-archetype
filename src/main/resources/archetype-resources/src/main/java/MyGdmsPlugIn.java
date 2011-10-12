package ${package};

import org.gdms.data.DataSourceFactory;
import org.gdms.plugins.GdmsPlugIn;

public class MyGdmsPlugIn implements GdmsPlugIn {

       private DataSourceFactory dsf;

        @Override
        public void load(DataSourceFactory dsf) {
                this.dsf = dsf;
        }

        @Override
        public void unload() {
        }

        @Override
        public String getName() {
                return "My gdms plugin";
        }

        @Override
        public String getVersion() {
                return "1.0";
        }
}
