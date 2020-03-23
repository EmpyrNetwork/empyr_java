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
@Documented( name="redemption", description="Represents a redemption that was applied to the transaction." )
public class RestRedemption extends RestBase
{
	public enum RedeemState {
		PENDING,		// Offer pending redemption on future purchases (referral fees may have been rewarded).
		COMPLETED,		// Offer has been fulfilled and cashback paid.
		REVERSED,		// Offer was reversed because it no longer met the criteria.
		UNQUALIFIED		// Offer didn't qualify (still possible to have fees).
	}
	
	@ApiField( "The id of the redemption." )
	public Integer id;
	
	@ApiField( "The state of the redemption (can be UNQUALIFIED, PENDING, COMPLETED, or REVERSED). PENDING would only be the case on a multi purchase offer.")
	public RedeemState state;
	
	@ApiField( "The date the redemption was added." )
	public Date dateAdded;
	
	@ApiField( value="The date that the redemption was reversed. Only supplied when the transaction is in fact reversed.", nullable=true )
	public Date dateReversed;
	
	@ApiField( "The cashback amount rewarded to the consumer." )
	public double cashbackAmount = 0;
	
	@ApiField( "The referral fee collected from the merchant." )
	public double referralFee = 0;
	
	@ApiField( "The amount of qualified spend applied to the redemption." )
	public double qualifiedSpend = 0;
	
	@ApiField( value="The amount of publisher commission earned.", optional=true )
	public double publisherCommission = 0;
	
	@ApiField( value="The amount of reseller commission earned.", optional=true )
	public double resellerCommission = 0;
	
	@ApiField( "The id of the offer that triggered this redemption." )
	public Integer offerId;

	@ApiField( value="The post dine survey link (if applicable).", optional=true )
	public String surveyLink;
}
