
[![Build Status](https://travis-ci.org/EmpyrNetwork/empyr_java.svg)](https://travis-ci.org/EmpyrNetwork/empyr_java.svg)

# Empyr Java Library

The Empyr Java library provides integration access to the Empyr API.

## Dependencies
* [ApacheHttp] - Apache Commons HTTPClient 3
* [Jackson] - Jackson Project (JSON mapping)

## Quick Start Example

````java
import com.empyr.api.EmpyrClient;
import com.empyr.api.model.RestApplication;
import com.empyr.api.model.RestResponse;

public class ClientQuickStart
{

	public static void main( String[] args )
	{
		EmpyrClient client = new EmpyrClient( "the_public_key", "the_private_key" );
		
		RestResponse<RestApplication> response = client.getApplicationInfo();
		
		if( response.meta.code == 200 )
		{
			System.out.println( "Application id: " + response.response.id );
		}else
		{
			System.out.println( "Unexpected API response: " + response );
		}
	}

}
````


## Maven
With Maven installed, this package can be built simply by running this command:
    mvn package
The resulting jar file will be producd in the directory named "target".

## In repositories
Maven Central, which should be enabled by default. No additional repositories are required.

## In dependencies

````xml
<dependency>
    <groupId>com.empyr</groupId>
    <artifactId>client</artifactId>
    <version>PUT VERSION HERE</version>
</dependency>
````

## Tests
Unit tests for this project are performed as a separate private project that runs against our development servers. These tests would fail in your local environment so they are not available for public consumption.

## License
See the LICENSE file.

   [ApacheHttp]: <http://hc.apache.org/httpclient-3.x/>
   [Jackson]: <https://github.com/FasterXML/jackson>

