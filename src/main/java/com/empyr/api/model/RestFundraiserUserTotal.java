package com.empyr.api.model;

import java.util.Date;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

@Documented( name="fundraiserUserTotal", description="Represents summary information about user/fundraiser donation activity." )
public class RestFundraiserUserTotal extends RestBase
{
	@ApiField( value="The date/month of the user total. Only relevant if queried this way.", nullable=true )
	public Date dateAdded;
	@ApiField( "The user that donated." )
	public RestCompactUser user;
	@ApiField( "The fundraiser being donated to." )
	public RestFundraiser fundraiser;
	@ApiField( "The amount of the donation" )
	public Double amount;
}
