/**
 * 
 */
package com.empyr.api.util;

import java.io.IOException;

import com.empyr.api.model.RestUrl;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

/**
 * @author jcuzens
 *
 */
public class RestUrlSerializer extends StdScalarSerializer<RestUrl>
{	
	public RestUrlSerializer()
	{
		super( RestUrl.class );
	}	
	
	@Override
	public void serialize( RestUrl value, JsonGenerator jgen,
			SerializerProvider provider ) throws IOException,
			JsonGenerationException
	{
		jgen.writeString( value.getValue() );
	}
}
