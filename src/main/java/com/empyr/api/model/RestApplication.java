package com.empyr.api.model;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

/**
 * @author jcuzens
 *
 */
@Documented( name="application", description="Basic information about an application." )
public class RestApplication extends RestBase
{
	@ApiField( "The id of this application." )
	public Integer id;
}
