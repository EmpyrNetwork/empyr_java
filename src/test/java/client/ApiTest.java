package client;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.empyr.api.EmpyrClient;

public class ApiTest
{

	private static EmpyrClient client = null;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		if( client == null )
		{
			client = new EmpyrClient( System.getenv( "CLIENT_ID" ), System.getenv( "CLIENT_SECRET" ) );
			client.setHost( System.getenv( "TEST_HOST" ) );
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		
	}

	@Before
	public void setUp() throws Exception
	{

	}

	@After
	public void tearDown() throws Exception
	{
	}
}
