package servlet;

import org.junit.Test;
import servlet.validator.Validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class ValidationTest {

    private Validator validator = new Validator();

    @Test
    public void emailCheckTest(){
        assertTrue(validator.checkEmail("1234@vosef.com"));
        assertFalse(validator.checkEmail("1234vosef.com"));
        assertFalse(validator.checkEmail("1234@vosefcom"));
    }

    @Test
    public void passCheckTest(){
        assertTrue(validator.checkPass("1234"));
        assertTrue(validator.checkPass("1234ffas"));
        assertFalse(validator.checkPass("123"));
        assertFalse(validator.checkPass("123sregrseres"));
    }

}
