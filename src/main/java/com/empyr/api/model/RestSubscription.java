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
@Documented( name="subscription", description="Represents a subscription on a business." )
public class RestSubscription extends RestBase
{
	@ApiField( "The id of the subscription." )
	public Integer id;
	
	@ApiField( value="The date the subscription starts.", nullable=true )
	public Date startsOn;
	
	@ApiField( value="The date that the subscription would end. When the subscription is being renewed if it is after the endsOn time it will be marked as EXPIRED and will not renew (no invoice will generate).", nullable=true )
	public Date endsOn;
	
	@ApiField( value="The date the subscription will next renew.", nullable=true )
	public Date nextRenewal;
	
	@ApiField( value="The date the subscription last rewewed.", nullable=true )
	public Date lastRenewal;
	
	@ApiField( "The state of the subscription [ACTIVE, EXPIRED, PAST_DUE].")
	public String state;
}
