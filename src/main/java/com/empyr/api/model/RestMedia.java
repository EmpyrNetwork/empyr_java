/**
 * 
 */
package com.empyr.api.model;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

/**
 * @author jcuzens
 *
 */
@Documented( name="media", description="Represents a media object like a photo or video." )
public class RestMedia extends RestBase
{	
	@ApiField( "The id of the media." )
	public Integer id;
	
	@ApiField( "A medium size image url 320x200." )
	public RestUrl mediumUrl;
	
	@ApiField( "A large size image url 960x640." )
	public RestUrl largeUrl;
	
	@ApiField( "The type of the media (like LOGO, AMBIANCE, FOOD etc.)." )
	public String type;
	
}
