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
@Documented( name="billingAccount", description="A billing account record that can be tied to a business for the purpose of billing and invoice notification." )
public class RestBillingAccount
{
	@ApiField( "The id of the billing account. This should be used to associate a business with an account." )
	public Integer id;
	
	@ApiField( "The name of the billing account." )
	public String name;
	
	@ApiField( "The accounting email that will be recieving the invoices." )
	public String email;
	
	@ApiField( "Payment method. Indicates the method of payment for this account. (MANUAL, BILLING_DETAIL)." )
	public String paymentMethod;
}
