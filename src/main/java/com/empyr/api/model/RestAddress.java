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
@Documented( name="address", description="Represents an address." )
public class RestAddress extends RestBase
{
	@ApiField( value="The street address such as 9645 Scranton Road.", nullable=true )
	public String streetAddress;
	@ApiField( "The postalcode such as 92009." )
	public String postalCode;
	@ApiField( "The city" )
	public String city;
	@ApiField( "The two letter state abbreviation." )
	public String state;
	@ApiField( "The metro id" )
	public Integer metroId;
	@ApiField( "The name of the metro" )
	public String metroName;	
	@ApiField( "An image representing the metro." )
	public RestUrl metroImage;
	@ApiField( "Whether the metro is currently active." )
	public boolean metroActive;
}
