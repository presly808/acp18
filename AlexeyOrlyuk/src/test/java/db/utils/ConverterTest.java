package db.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConverterTest {

    @Test
    public void convertToSQLTypeFromPrimitiveType() throws Exception {
        assertEquals("INT", Converter.convertToSQLType(int.class));
        assertEquals("DOUBLE", Converter.convertToSQLType(double.class));
    }

    @Test
    public void convertToSQLTypeFromReferenceType() throws Exception {
        assertEquals("VARCHAR(255)", Converter.convertToSQLType(String.class));
        assertEquals("REF", Converter.convertToSQLType(Object.class));
    }

    @Test
    public void convertSQLTypeToPrimitiveType() throws Exception {
        assertEquals(int.class, Converter.convertSQLType("INT"));
        assertEquals(double.class, Converter.convertSQLType("DOUBLE"));
    }

    @Test
    public void convertSQLTypeToReferenceType() throws Exception {
        assertEquals(String.class, Converter.convertSQLType("VARCHAR(255)"));
        assertEquals(String.class, Converter.convertSQLType("VARCHAR"));
        assertEquals(Object.class, Converter.convertSQLType("REF"));
    }

}