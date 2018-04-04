/**
 * 
 */
package com.empyr.api.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.empyr.api.Request;
import com.empyr.api.model.RestMeta;
import com.empyr.api.model.RestResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * @author jcuzens
 *
 */
public class JacksonRequestAdapter implements RequestAdapter
{
	private static transient final Log log = LogFactory.getLog( JacksonRequestAdapter.class );
	private ObjectMapper mapper = ApiJacksonObjectMapper.getInstance( null, new RestUrlDeSerializer() );
	
	/* (non-Javadoc)
	 * @see com.empyr.api.util.RequestAdapter#adapt(java.lang.Class, java.lang.String)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public <T> void adapt( Request<T> r, String strToAdapt )
	{	
		try
		{
			JsonNode tree = mapper.readTree( strToAdapt );
			
			RestResponse<T> rr = new RestResponse<T>();			
			rr.meta = mapper.convertValue( tree.get( "meta" ), RestMeta.class );
			
			Map<String,Object> expects = r.getExpected();
			Map<String,Object> response = new HashMap<String,Object>();
			
			JsonNode responseTree = tree.get( "response" );
			for( Map.Entry<String, Object> entry : expects.entrySet() )
			{
				Object value = null;
				
				if( entry.getValue() instanceof TypeReference<?> )
				{
					JavaType type = TypeFactory.defaultInstance().constructType( ((TypeReference<?>)entry.getValue()).getType() );
					value = mapper.convertValue( responseTree.get( entry.getKey() ), type );
				}else
				{
					Class<?> clazz = (Class<?>)entry.getValue();
					value = mapper.convertValue( responseTree.get( entry.getKey() ), clazz );
				}
				
				if( value != null )
				{
					response.put( entry.getKey(), value );
				}
			}
			
			if( response.size() > 1 )
			{
				rr.response = (T)response;
			}
			else if( response.size() == 1 )
			{
				rr.response = (T)response.entrySet().iterator().next().getValue();
			}
			
			r.setResponse( rr );
		} catch( Exception e )
		{
			log.warn( "Exception caught trying to parse response: " + strToAdapt, e );
			throw new RuntimeException( e );
		}
	}

	@Override
	public <T> T adapt( Class<T> clazz, String response )
	{
		try
		{
			return mapper.readValue( response, clazz );
		} catch( Exception e )
		{
			log.warn( "Exception caught trying to parse response: " + response, e );
			throw new RuntimeException( e );
		}
	}
}
