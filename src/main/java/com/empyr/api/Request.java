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
public class Request<T> extends HttpRequest
{
	private RestResponse<T> response;
	private boolean requiresToken = false;
	private Map<String,Object> expected = new HashMap<String,Object>();
	
	protected Request( MethodType t, String endPoint, boolean requiresToken )
	{
		super( t, endPoint );
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
		super.addParams( params );
		return this;
	}
	
	public Request<T> addParams( Object ... params )
	{
		super.addParams( params );
		return this;
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
