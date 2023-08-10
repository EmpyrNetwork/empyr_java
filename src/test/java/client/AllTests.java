package client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith( Suite.class )
@SuiteClasses( { ApiTest.class } )
public class AllTests
{
	@Test
    public void test() {
        // does nothing
    }
}
