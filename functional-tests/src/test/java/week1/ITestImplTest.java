package week1;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by serhii on 15.01.18.
 */
public class ITestImplTest {
    @Test
    public void sum() throws Exception {
        ITest iTest = new ITestImpl();
        Assert.assertThat(iTest.sum(1,3), CoreMatchers.equalTo(4));
    }

}