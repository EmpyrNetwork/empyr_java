/**
 * 
 */
package com.empyr.api.util;

import java.io.IOException;
import java.util.Date;

import com.empyr.api.model.RestUrl;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

/**
 * @author jcuzens
 *
 */
public class ApiJacksonObjectMapper extends ObjectMapper
{
	private StdScalarSerializer<RestUrl> urlSerializer = null;
	private StdScalarDeserializer<RestUrl> urlDeserializer = null;
	
	/**
	 * @return the urlSerializer
	 */
	public StdScalarSerializer<RestUrl> getUrlSerializer()
	{
		return urlSerializer;
	}

	/**
	 * @param urlSerializer the urlSerializer to set
	 */
	public void setUrlSerializer( StdScalarSerializer<RestUrl> urlSerializer )
	{
		this.urlSerializer = urlSerializer;
	}

	/**
	 * @return the urlDeserializer
	 */
	public StdScalarDeserializer<RestUrl> getUrlDeserializer()
	{
		return urlDeserializer;
	}

	/**
	 * @param urlDeserializer the urlDeserializer to set
	 */
	public void setUrlDeserializer( StdScalarDeserializer<RestUrl> urlDeserializer )
	{
		this.urlDeserializer = urlDeserializer;
	}
	
	public final class UrlStringModule extends SimpleModule
	{
		public UrlStringModule()
		{
			if( urlSerializer != null )
			{
				addSerializer( RestUrl.class, urlSerializer );
			}
			
			if( urlDeserializer != null )
			{
				addDeserializer( RestUrl.class, urlDeserializer );
			}
		}
	}
	
	public ApiJacksonObjectMapper()
	{
		AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
	    
		getDeserializationConfig().with( primary );
		getSerializationConfig().with( primary );
		
		configure( SerializationFeature.INDENT_OUTPUT, true );
		configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
		setSerializationInclusion( Include.NON_NULL );
	}

	public static ObjectMapper getInstance( StdScalarSerializer<RestUrl> serializer, StdScalarDeserializer<RestUrl> deserializer )
	{
		ObjectMapper mapper = new ApiJacksonObjectMapper();
		
		{
			SimpleModule module = new SimpleModule( "UrlModule" );
			
			if( serializer != null )
			{
				module.addSerializer( RestUrl.class, serializer );
			}
			
			if( deserializer != null )
			{
				module.addDeserializer( RestUrl.class, deserializer );
			}
			
			mapper.registerModule( module );
		}
		
		{
			SimpleModule module = new SimpleModule( "DecimalModule" );
			
			module.addSerializer( Double.class, new StdScalarSerializer<Double>(Double.class){
					@Override
					public void serialize( Double value, JsonGenerator jgen,
							SerializerProvider provider ) throws IOException,
							JsonGenerationException
					{
						if( value != null )
						{
							double rval = (Math.round( value * 100 )) / 100.0;
							jgen.writeNumber( rval );
						}
					}
				} );
			
			module.addSerializer( Float.class, new StdScalarSerializer<Float>(Float.class){
				@Override
				public void serialize( Float value, JsonGenerator jgen,
						SerializerProvider provider ) throws IOException,
						JsonGenerationException
				{
					if( value != null )
					{
						double rval = (Math.round( value * 100 )) / 100.0;
						jgen.writeNumber( rval );
					}
				}
			} );			
			
			mapper.registerModule( module );
		}
		
		{
			SimpleModule module = new SimpleModule( "DateModule" );
			
			module.addSerializer( Date.class, new StdScalarSerializer<Date>(Date.class){
					@Override
					public void serialize( Date value, JsonGenerator jgen,
							SerializerProvider provider ) throws IOException,
							JsonGenerationException
					{
						if( value != null )
						{
							long time = value.getTime() / 1000;
							jgen.writeNumber( time );
						}
					}
				} );	
			
			module.addDeserializer( Date.class, new StdScalarDeserializer<Date>(Date.class)
			{
				@Override
				public Date deserialize( JsonParser jp,
						DeserializationContext ctxt ) throws IOException,
						JsonProcessingException
				{
					long time = (jp.getLongValue() * 1000);
					return new Date( time );
				}
			} );
			
			mapper.registerModule( module );
		}		
		
		return mapper;
	}
}
