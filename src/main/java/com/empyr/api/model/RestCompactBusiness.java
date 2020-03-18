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
@Documented( name="compactVenue", description="A compact version of the venue record." )
public class RestCompactBusiness extends RestBase
{
	@ApiField( "The id of the venue." )
	public Integer id;
	
	@ApiField( "The name of the venue." )
	public String name;
	
	@ApiField( "The latitude of the venue." )
	public double latitude;
	
	@ApiField( "The longitude of the venue." )
	public double longitude;
	
	@ApiField( "This only applies to searching and returns the distance from the source of the search." )
	public Float distance;
	
	@ApiField( value="The phone number for the venue.", nullable=true)
	public String phone;
	
	@ApiField( "The thumbnail url that is the logo of this venue." )
	public RestUrl thumbnailUrl;
	
	@ApiField( "The average rating of the venue." )
	public Double rating;	
	
	@ApiField( "The number of ratings for the venue." )
	public Integer ratingCount;
	
	@ApiField( value="The yelp foreign id of the venue.", nullable=true )
	public String yelpId;
	
	@ApiField( "Address of the venue." )
	public RestAddress address;
	
	@ApiField( "The application id that enrolled this business." )
	public Integer applicationId;
	
	@ApiField( "The business token that was assigned to the business by the partner. Will only be supplied on partner's merchants." )
	public String businessToken;
}
