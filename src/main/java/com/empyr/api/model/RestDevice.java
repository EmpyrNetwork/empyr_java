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
@Documented( name="device", description="A device that is associated with a user's account." )
public class RestDevice extends RestBase
{
	public enum DeviceType {
		IOS,
		ANDROID,
		ANDROIDv2
	}
	
	@ApiField( "The id of the device." )
	public Integer id;
	@ApiField( "The device token that is used for push notifications." )
	public String deviceToken;
	@ApiField( "The type of the device." )
	public DeviceType deviceType;
	@ApiField( "The date the device was added to the system." )
	public Date dateAdded;
}
