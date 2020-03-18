/**
 * 
 */
package com.empyr.api.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;
import com.empyr.api.model.RestCard.CardType;

/**
 * @author jcuzens
 *
 */
@Documented( name="venue", description="Represents a venue/location/business. Includes all fields of the compactVenue." )
public class RestBusiness extends RestCompactBusiness
{	
	@ApiField( "The category of the business." )
	public String primaryCategory;	
	
	@ApiField( "A list of all the business categories." )
	public String[] categories;
	
	@ApiField( "Whether this business is in the users bookmarks. Only populated on the Venue GET call" )
	public Boolean bookmarked;
	
	@ApiField( "The website of the venue." )
	public String website;
	
	@ApiField( "A description of the venue." )
	public String description;
	
	@ApiField( "Represents the price range of the venue." )
	public String priceRange;
	
	@ApiField( "The menu for the venue." )
	public RestUrl menuUrl;
	
	@ApiField( "Address of the venue." )
	public RestMedia[] medias;
	
	@ApiField( "Consumer buzz associated with the venue." )
	public String [] buzz;
	
	@ApiField( "Various features that the venue offers." )
	public String [] features;
	
	@ApiField( "The noise level expected at this venue." )
	public String [] noiseLevel;
	
	@ApiField( "The kind of ambiance the venue provides." )
	public String [] ambiance;
	
	@ApiField( "Whether the venue serves breakfast, lunch, dinner." )
	public String [] serves;
	
	@ApiField( "The kind of acceptable attire at the venue." )
	public String [] attire;
	
	@ApiField( "The MOGL accepted payment types at the venue." )
	public String [] acceptedCards;
	
	@ApiField( value="What they're known for.", nullable=true )
	public String knownFor;
	
	@ApiField( "Best nights to go to this venue." )
	public String [] bestNights;
	
	@ApiField( "Olson timezone of the business." )
	public String timezone;
	
	@ApiField( "Timezone offset from GMT." )
	public int timezoneOffset;
	
	@ApiField( "A simple representation of the hours of operation" )
	public Map<String,String> simpleHours = new LinkedHashMap<String,String>();
	
	@ApiField( "The default reward percentage for this business." )
	public double defaultDiscount;
	
	@ApiField( value="A list of all the current day's variable rewards offers at the business", type=RestVariableReward.class )
	public List<RestVariableReward> rewards = new ArrayList<RestVariableReward>();
	
	@ApiField( value="A list of the complete rewards schedule for the business", type=RestVariableReward.class )
	public Map<String,List<RestVariableReward>> rewardsSchedule = new LinkedHashMap<String,List<RestVariableReward>>();
	
	@ApiField( value="Indicates whether the business is open or closed currently.")
	public Boolean closed;
	
	@ApiField( value="State of the business" )
	public String status;
	
	@ApiField( value="A list of offers that this business is offering.", type=RestOffer.class)
	public List<RestOffer> offers = new ArrayList<RestOffer>();
	
	@ApiField( value="This offer should be published" )
	public boolean publish;
	
	@ApiField( value="Current day (business local time). Expressed as [SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY]" )
	public String businessLocalDay;
	
	@ApiField( value="Current time (business local time). Expressed as HHMM" )
	public String businessLocalTime;
	
	@ApiField( value="The chain that this business belongs to." )
	public RestChain chain;
	
}
