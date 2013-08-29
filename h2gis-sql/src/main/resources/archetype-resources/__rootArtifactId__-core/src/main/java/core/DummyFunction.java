#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.vividsolutions.jts.geom.Geometry;
import org.h2gis.h2spatialapi.DeterministicScalarFunction;

/**
 * Function sample. Return the area of the parameter.
 */
public class DummyFunction extends DeterministicScalarFunction {
    /**
     * Default constructor.
     */
    public DummyFunction() {
        addProperty(PROP_REMARKS, "Function sample, Return the area of the parameter.");
    }

    @Override
    public String getJavaStaticMethod() {
        return "test";
    }

   /**
    * Function sample. Return the same value as parameters.
    */
    public static double test(Geometry geometry) {
        return geometry.getArea();
    }
}
