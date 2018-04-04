/**
 * 
 */
package com.empyr.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jcuzens
 *
 */
public class CommonsHttpRequestUtil implements HttpRequestUtil
{
	private static transient final Log log = LogFactory.getLog( CommonsHttpRequestUtil.class );
	
	private static MultiThreadedHttpConnectionManager connManager;
	private static HttpClient client;
	
	static
	{
		connManager = new MultiThreadedHttpConnectionManager();
		connManager.setMaxTotalConnections( 10 );
		connManager.setMaxConnectionsPerHost( 5 );
		
		client = new HttpClient( connManager );
	}
	
	private HttpMethod createMethod(
			MethodType methodType,
			String endPoint,
			Map<String,Object> params
		)
	{
		HttpMethod method = null;
		List<FilePart> fileParts = new ArrayList<FilePart>();
		List<StringPart> stringParts = new ArrayList<StringPart>();
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if( params != null )
		{
			for( Map.Entry<String, Object> entry : params.entrySet() )
			{
				if( entry.getValue() instanceof String )
				{
					pairs.add( new NameValuePair( entry.getKey(), (String)entry.getValue() ) );
					stringParts.add( new StringPart( entry.getKey(), (String )entry.getValue() ) );
				}
				else if( entry.getValue() instanceof FileUpload )
				{
					FileUpload fu = (FileUpload)entry.getValue();
					FilePart fp = new FilePart( "file", fu );
					fp.setContentType( fu.getContentType() );
					fileParts.add( fp );
				}
				else if( entry.getValue().getClass().isArray() )
				{
					int length = Array.getLength( entry.getValue() );
					for( int i = 0; i < length; i++ )
					{
						pairs.add( new NameValuePair( entry.getKey(), (String)Array.get( entry.getValue(), i ) ) );
						stringParts.add( new StringPart( entry.getKey(), (String)Array.get( entry.getValue(), i ) ) );
					}
					
				}
				else
				{
					throw new RuntimeException( "Unrecognized type for post: " + entry.getValue().getClass().getName() );
				}
			}
		}
		
		switch( methodType )
		{
			case GET:
				method = new GetMethod();
				break;
			case DELETE:
				method = new DeleteMethod();
				break;
			case POST:
				method = new PostMethod();
				break;
			case PUT:
				method = new PutMethod();
				break;
		}
		
		try
		{
			method.setURI( new URI( endPoint, true ) );
		} catch( URIException e )
		{
			throw new RuntimeException( e );
		}
		
		if( method instanceof PostMethod )
		{
			if( fileParts.size() > 0 )
			{
				// We need to send multpart form encoded data.
				List<Part> parts = new ArrayList<Part>();
				parts.addAll( fileParts );
				parts.addAll( stringParts );
				MultipartRequestEntity mre = new MultipartRequestEntity( parts.toArray( new Part[]{} ), method.getParams() );
				((PostMethod)method).setRequestEntity( mre );
			}else
			{
				((PostMethod)method).addParameters( pairs.toArray( new NameValuePair[]{} ) );
			}
		}else
		{
			method.setQueryString( pairs.toArray( new NameValuePair[]{} ) );
			String [] endPointParts = endPoint.split( "\\?" );
			if( endPointParts.length > 1 )
			{
				String realQueryString = method.getQueryString() + "&" + endPointParts[1];
				method.setQueryString( realQueryString );
			}
		}
		
		method.setRequestHeader( "Accept", "application/json" );
		
		return method;		
	}
	
	private String sendRequest( HttpMethod method ) throws IOException
	{
		String result = null;
		
		try
		{
			List<String> authPrefs = new ArrayList<String>(1);
			authPrefs.add( AuthPolicy.BASIC );
			client.getParams().setParameter( AuthPolicy.AUTH_SCHEME_PRIORITY, authPrefs );
			client.getParams().setParameter( "http.protocol.content-charset", "UTF-8" );
			
			int statusCode = client.executeMethod( method );
			result = convertStreamToString( method.getResponseBodyAsStream() );
	
			if( statusCode < 200 || statusCode > 299 )
			{
				log.debug( "Unexpected response: " + statusCode + "\n" + result );
			}
		}finally
		{
			method.releaseConnection();
		}
		
		return result;
	}	
	
	private String convertStreamToString( InputStream is ) 
	{
	    java.util.Scanner s = new java.util.Scanner( is ).useDelimiter( "\\A" );
	    return s.hasNext() ? s.next() : "";
	}
	
	public String executeMethod( MethodType type, String endPoint, Map<String,Object> params )
	{
		String result = null;
		HttpMethod method = createMethod( type, endPoint, params );
		
		try
		{
			result = sendRequest( method );
		}
		catch( Exception e )
		{
			log.warn( "Exception caught executing client method: " + endPoint + " with params: " + params, e );
			throw new RuntimeException( e );
		}
		
		return result;
	}	
	
}
