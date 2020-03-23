/**
 * 
 */
package com.empyr.api.model;

import java.util.Date;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

/**
 * @author jarrodcuzens
 *
 */
@Documented( name="fundraiser", description="Represents a fundraiser that a user can donate to." )
public class RestFundraiser extends RestBase
{
	@ApiField( "The id of the fundraiser." )
	public Integer id;
	@ApiField( "The name of the fundraiser.")
	public String name;
	@ApiField( "A description of the fundraiser." )
	public String description;
	@ApiField( "A code that can be used to help people sign up to the fundraiser." )
	public String fundraiserCode;
	@ApiField( "The goal for the fundraiser." ) 
	public Double goalAmount;
	@ApiField( value="A thumbnail of the fundraiser.", optional=true )
	public RestUrl thumbnailUrl;
	@ApiField( value="The website for the fundraiser.", nullable=true )
	public String website;
	@ApiField( value="A date when the fundraiser would end.", nullable=true )
	public Date endDate;
	@ApiField( "The organizer of the fundraiser." )
	public RestCompactUser organizer;
	
	@ApiField( "The summary information about this fundraiser. Only appended in search." )
	public RestFundraiserSummary fundraiserSummary;
}
