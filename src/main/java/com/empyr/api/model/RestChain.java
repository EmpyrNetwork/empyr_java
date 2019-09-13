package com.empyr.api.model;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

@Documented( name="chain", description="Represents a business chain." )
public class RestChain extends RestBase
{
	@ApiField( "The identifier of the chain." )
	public Integer id;
	
	@ApiField( "The name of the chain." )
	public String name;
}
