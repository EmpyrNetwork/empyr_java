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
@Documented( name="transactionReport", description="Returns a summary of aggregate information over transactions." )
public class RestTransactionReport extends RestBase
{
	@ApiField( value="The value of the grouped by property.", optional=true )
	public String groupedByValue;
	
	@ApiField( "Cashback awarded to customers." )
	public double cashback = 0;
	
	@ApiField( "Cashback billed to merchants." )
	public double cashbackBilled = 0;
	
	@ApiField( value="Most recent transaction date.", nullable=true )
	public Date mostRecentTxDate;
	
	@ApiField( "The total number of unique customers." )
	public long numCustomers = 0;
	
	@ApiField( "The number of first time customers." )
	public long numFirstCustomers = 0;
	
	@ApiField( "The number of transactions." )
	public long numTransactions = 0;
	
	@ApiField( "The total referral fee billed." )
	public double referralFee = 0;
	
	@ApiField( "The amount of revenue earned." )
	public double revenue = 0;
	
	@ApiField( "The business id of the business in question (if applicable)." )
	public long businessId;
	
	@ApiField( "The period (if applicable)." )
	public Date period;
}
