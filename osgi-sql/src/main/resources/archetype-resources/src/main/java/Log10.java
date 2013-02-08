package ${package};

import org.gdms.data.DataSourceFactory;
import org.gdms.data.types.Type;
import org.gdms.data.values.Value;
import org.gdms.data.values.ValueFactory;
import org.gdms.sql.function.FunctionException;
import org.gdms.sql.function.math.AbstractScalarMathFunction;

/**
 * Custom SQL Function
 */
public class Log10 extends AbstractScalarMathFunction {

    public Value evaluate(DataSourceFactory dsf, Value... args) throws FunctionException {
        if (args[0].isNull()) {
            return ValueFactory.createNullValue();
        } else {
            final double value = args[0].getAsDouble();
            return ValueFactory.createValue(Math.log10(value));
        }
    }

    @Override
    public String getName() {
        return "PL_Log10";
    }

    @Override
    public int getType(int[] types) {
        return Type.DOUBLE;
    }

    @Override
    public String getDescription() {
        return "Returns the base 10 logarithm of a double value";
    }

    @Override
    public String getSqlOrder() {
        return "select PL_Log10(myNumericField) from myTable;";
    }
}