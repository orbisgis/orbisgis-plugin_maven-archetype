package ${package};

import junit.framework.Assert;
import org.gdms.data.values.Value;
import org.gdms.data.values.ValueFactory;
import org.junit.Test;

/**
 * Unit test of the Log10 function
 */
public class Log10Test {
    private double evaluate(double value) throws Exception {
        return new Log10().evaluate(null,new Value[]{ValueFactory.createValue(value)}).getAsDouble();
    }
    @Test
    public void testEvaluate() throws Exception {
        // Evaluate Log(1)
        Assert.assertEquals(0,
                evaluate(1.),
                1.e-20);
    }
}
