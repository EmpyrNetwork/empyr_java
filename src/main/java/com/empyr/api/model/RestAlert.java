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
@Documented( name="alert", description="Represents an alert." )
public class RestAlert
{	
	@ApiField( "The id of the target of this alert.")
	public Long targetId;
	@ApiField( "The date of the alert." )
	public Date dateAdded;
	@ApiField( "An href representing a link for this alert.")
	public String href;
	@ApiField( "A textual title of the alert." )
	public String title;
	@ApiField( "A textual description of the alert.")
	public String description;
	@ApiField( "The type of the alert.")
	public String type;
}
