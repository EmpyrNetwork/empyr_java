package com.empyr.api;

import java.util.HashMap;
import java.util.Map;

import com.empyr.api.model.RestResponse;
import com.empyr.api.util.FileUpload;
import com.empyr.api.util.MethodType;
import com.empyr.api.util.TypeReference;

/**
 * Builder class for building API requests to the Empyr backend.
 * 
 * @see com.empyr.api.EmpyrClient for examples.
 * 
 * @author jarrodcuzens
 *
 * @param <T> The type of the requested response which would be serialized out of the API call.
 */
public class Request<T>
{
	private MethodType method;
	private String endPoint;
	private Map<String,Object> requestParams = new HashMap<String,Object>();
	private Map<String,Object> expected = new HashMap<String,Object>();
	private RestResponse<T> response;
	private boolean requiresToken = false;
	
	protected Request( MethodType t, String endPoint, boolean requiresToken )
	{
		this.method = t;
		this.endPoint = endPoint;
		this.requiresToken = requiresToken;
	}
	
	public static <F> Request<F> createRequest( MethodType get, String endpoint )
	{
		return createRequest( get, endpoint, false );
	}
	
	public static <F> Request<F> createRequest( MethodType get, String endpoint, boolean requiresToken )
	{
		return createRequest( get, endpoint, requiresToken, null );
	}
	
	public static <F> Request<F> createRequest( MethodType get, String endpoint, boolean requiresToken, String userToken )
	{
		Request<F> r = new Request<F>( get, endpoint, requiresToken );
		if( userToken != null )
		{
			r.addParams( "user_token", userToken );
		}
		return r;
	}
	
	public Request<T> addParams( Map<String,Object> params ) 
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
				if( !(value instanceof FileUpload) && !(value.getClass().isArray()) )
				{
					value = String.valueOf( value );
				}
				
				requestParams.put( entry.getKey(), value );
			}
		}
		
		return this;
	}
	
	public Request<T> addParams( Object ... params )
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
				if( !(value instanceof FileUpload) && !(value.getClass().isArray()))
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
	
	/**
	 * @return the expected
	 */
	public Map<String, Object> getExpected()
	{
		return expected;
	}

	public Request<T> expects( String returnKey, Class<?> response )
	{
		expected.put( returnKey, response );
		return this;
	}
	
	public <F> Request<T> expects( String returnKey, TypeReference<F> type )
	{
		expected.put( returnKey, type );
		return this;
	}

	public RestResponse<T> getResponse()
	{	
		return response;
	}
	
	public void setResponse( RestResponse<T> response )
	{
		this.response = response;
	}

	/**
	 * @return the requiresToken
	 */
	public boolean isRequiresToken()
	{
		return requiresToken;
	}
}
