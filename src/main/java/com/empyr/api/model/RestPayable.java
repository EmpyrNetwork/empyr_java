/**
 * 
 */
package com.empyr.api.model;

import java.util.Date;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

/**
 * @author jcuzens
 *
 */
@Documented( name="payable", description="Represents a payment to a user." )
public class RestPayable extends RestBase
{
	@ApiField( "The id of the payable." )
	public long id;
	
	@ApiField( "The amount that was paid back." )
	public Double amount;
	
	@ApiField( "The date of the payment." )
	public Date date;
	
	@ApiField( value="The payment description", optional=true )
	public String paymentDescription;
	
	@ApiField( "The payment account details" )
	public String paymentAccountDetails;
	
	@ApiField( "The status of the payment" )
	public String paymentStatus;
	
	@ApiField( value="The card that the payment was paid (except if the payment was made to a different reward type).", optional=true )
	public RestCard card;
	
	@ApiField( "The user that is being payed out." )
	public RestCompactUser user;
	
	@ApiField( "The partner details associated with this payable when created." )
	public String details;
}
