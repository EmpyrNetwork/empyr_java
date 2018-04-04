/**
 * 
 */
package com.empyr.api.util;

import java.io.IOException;

import com.empyr.api.model.RestUrl;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

/**
 * @author jcuzens
 *
 */
public class RestUrlDeSerializer extends StdScalarDeserializer<RestUrl>
{
	public RestUrlDeSerializer()
	{
		super( RestUrl.class );
	}

	@Override
	public RestUrl deserialize( JsonParser jp, DeserializationContext ctxt )
			throws IOException, JsonProcessingException
	{
		JsonNode node = jp.readValueAsTree();

		String value = null;
		if( node.isObject() )
		{
			value = node.get( "value" ).textValue();
		}else
		{
			value = node.textValue();
		}
		
		return new RestUrl( value );
	}
}
