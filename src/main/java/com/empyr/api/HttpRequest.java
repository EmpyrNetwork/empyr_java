/**
 * 
 */
package com.empyr.api;

import java.util.HashMap;
import java.util.Map;

import com.empyr.api.util.FileUpload;
import com.empyr.api.util.MethodType;

/**
 * @author jarrodcuzens
 *
 */
public class HttpRequest
{
	private MethodType method;
	private String endPoint;
	private Map<String,Object> requestParams = new HashMap<String,Object>();
	
	public HttpRequest( MethodType method, String endPoint )
	{
		this.method = method;
		this.endPoint = endPoint;
	}

	public static HttpRequest createHttpRequest( MethodType get, String endpoint )
	{
		return new HttpRequest( get, endpoint );
	}
	
	public HttpRequest addParams( Map<String,Object> params ) 
	{
		if( params == null || params.size() == 0 )
		{
			return this;
		}
		
		for( Map.Entry<String, Object> entry : params.entrySet() )
		{
			if( entry.getValue() != null )
			{
				Object value = entry.getValue();
				if( !(value instanceof FileUpload) && !(value.getClass().isArray()) && !(value instanceof Iterable) )
				{
					value = String.valueOf( value );
				}
				
				requestParams.put( entry.getKey(), value );
			}
		}
		
		return this;
	}
	
	public HttpRequest addParams( Object ... params )
	{
		if( params == null || params.length <= 0 )
		{
			return this;
		}
		
		if( params.length % 2 != 0 )
		{
			throw new RuntimeException( "Uneven param keys to values.");
		}
		
		for( int i = 0; i < params.length; i+=2 )
		{
			// Skip any null value entries.
			if( params[i+1] != null )
			{
				Object value = params[i + 1];
				if( !(value instanceof FileUpload) && !(value.getClass().isArray()) && !(value instanceof Iterable) )
				{
					value = String.valueOf( value );
				}
				
				requestParams.put( String.valueOf( params[i] ), value );
			}			
		}
		
		return this;
	}
	
	/**
	 * @return the method
	 */
	public MethodType getMethod()
	{
		return method;
	}

	/**
	 * @return the endPoint
	 */
	public String getEndPoint()
	{
		return endPoint;
	}

	/**
	 * @return the requestParams
	 */
	public Map<String, Object> getRequestParams()
	{
		return requestParams;
	}
}
