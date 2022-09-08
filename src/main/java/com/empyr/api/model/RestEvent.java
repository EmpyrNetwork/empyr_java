/**
 * 
 */
package com.empyr.api.model;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

/**
 * @author jarrodcuzens
 *
 */
@Documented( name="event", description="Represents an event in the system." )
public class RestEvent extends RestBase
{
	public enum EventType
	{
		AUTHORIZED,
		CLEARED,
		REMOVED,
		REMOVED_DUP,
		PAYMENT,
		CARD_STATUS,
		HIDDEN_SUMMARY,
		STATS_REPORT,
		BUSINESS_REVIEWED,
		BUSINESS_APPROVED
	}
	
	@ApiField( "The type of the event." )
	public EventType type;
	
	@ApiField( value="If the event was associated with a transaction then this will be populated.", type=RestTransaction.class  )
	public RestTransaction transaction;
	
	@ApiField( value="If the event was a payment then this will be populated", type=RestPayable.class )
	public RestPayable payable;
	
	@ApiField( value="If the event was a successful card registration then this will be populated", type=RestCard.class )
	public RestCard card;
	
	@ApiField( value="If the event was a HIDDEN_SUMMARY this field will be populated.", type=RestTransactionReport.class )
	public RestTransactionReport transactionReport;
	
	@ApiField( value="If the event was a STATS_REPORT this field will be populated.", type=RestBusinessStatsReport.class )
	public RestBusinessStatsReport statsReport;
	
	@ApiField( value="If the event was a BUSINESS_APPROVED this field will be populated.", type=RestBusiness.class )
	public RestBusiness businessApproved;

	@ApiField( value="If the event was a BUSINESS_REVIEWED this field will be populated.", type=RestBusiness.class )
	public RestBusiness businessReviewed;
	
}
