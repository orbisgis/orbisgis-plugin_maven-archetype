#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import org.h2gis.h2spatial.CreateSpatialExtension;
import org.h2gis.h2spatial.ut.SpatialH2UT;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test of sample function.
 * @author Nicolas Fortin
 */
public class DummyFunctionTest {
    private static Connection connection;

    @BeforeClass
    public static void tearUp() throws Exception {
        connection = SpatialH2UT.createSpatialDataBase("DummyFunctionTest", false);
        CreateSpatialExtension.registerFunction(connection.createStatement(), new DummyFunction(), "");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void testDummy() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select DummyFunction(?) myValue");
        ps.setString(1, "POLYGON((0 0,10 0,10 10,0 10,0 0))");
        ResultSet rs = ps.executeQuery();
        assertTrue(rs.next());
        try {
            assertEquals(100, rs.getDouble("myValue"), 1e-12);
        } finally {
            rs.close();
        }
    }
}
