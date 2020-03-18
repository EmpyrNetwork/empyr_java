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
@Documented( name="offer", description="Represents an offer from the business." )
public class RestOffer extends RestBase
{
	public enum OfferRewardType {
		FIXED,		// Fixed reward amount.
		PERCENT		// Percentage of the total transaction.
	}	
	
	@ApiField( "The id of the offer." )
	public Integer id;
	
	@ApiField( "The type of the reward value." )
	public OfferRewardType rewardType;
	
	@ApiField( "The reward that the user will earn when this offer is redeemed." )
	public Double rewardValue;
	
	@ApiField( "The maximum possible reward value for any given redemption." )
	public Double rewardMax;
	
	@ApiField( value="The date the that reward or fee last changed on. Only used on dynamic offers. The value will also be null if it is dynamic but the reward has never been changed since it was created.", nullable=true )
	public Date dateRewardChanged;
	
	@ApiField( "The type of the marketing fee." )
	public OfferRewardType feeType;
	
	@ApiField( "The marketing fee value." )
	public Double feeValue;
	
	@ApiField( "The marketing fee maximum." )
	public Double feeMax;
	
	@ApiField( "The fine print represents a textual description of the offer requirements." )
	public String finePrint;
	
	@ApiField( value="If present, this image must be displayed next to the offer.", nullable=true )
	public String imageUrl;
	
	@ApiField( "Whether or not the offer is a click-to-activate style offer which would require a button and a call to the /user/activate endpoint." )
	public boolean requiresActivation;
	
	@ApiField( "Whether this offer is a basic offer. These offers are very simple and only have max reward and may have a variable reward calendar specified but no other limitations." )
	public boolean basic = true;
	
	@ApiField( "Has the more detailed offer criteria. This is useful for partners that apply the criteria themselves or want to build custom UIs to represent the offer criteria." )
	public RestOfferDetails details;
	
	@ApiField( "Convenience field that determines if the schedule (if defined) would yield a reward if the customer transacted at this moment." )
	public Boolean excludedBySchedule;
	
	@ApiField( "This will only be populated when: 1) There is a loggedInUser or userToken. 2) It is a search OR a direct business lookup." )
	public RestOfferLink link;
	
	@ApiField( "This will only be populated when returning offer links for a user." )
	public RestCompactBusiness venue;
}
