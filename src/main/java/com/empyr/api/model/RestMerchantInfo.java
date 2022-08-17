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
@Documented( name="merchantInfo", description="Represents the boarding details provided for a merchant." )
public class RestMerchantInfo extends RestBase
{
	@ApiField( value="The merchant id for the merchant.", optional=true )
	public String merchantId;
	@ApiField( value="Comma seperated list of Amex SE numbers for the merchant.", optional=true )
	public String amexId;
	@ApiField( value="Comma seperated list of VMID/VSID identifiers for the merchant.", optional=true )
	public String visa;
	@ApiField( value="Comma seperated list of Mastercard identifiers for the merchant.", optional=true )
	public String mastercard;
}
