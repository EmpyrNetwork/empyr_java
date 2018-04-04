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
	
	@ApiField( "The date the subscription starts." )
	public Date startsOn;
	
	@ApiField( "The date that the subscription would end. When the subscription is being renewed if it is after the endsOn time it will be marked as EXPIRED and will not renew (no invoice will generate)." )
	public Date endsOn;
	
	@ApiField( "The date the subscription will next renew." )
	public Date nextRenewal;
	
	@ApiField( "The date the subscription last rewewed." )
	public Date lastRenewal;
	
	@ApiField( "The state of the subscription [ACTIVE, EXPIRED, PAST_DUE].")
	public String state;
}
