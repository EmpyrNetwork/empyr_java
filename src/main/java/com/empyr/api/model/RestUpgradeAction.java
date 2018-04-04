/**
 * 
 */
package com.empyr.api.model;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;
import com.empyr.api.model.RestDevice.DeviceType;

/**
 * @author jarrodcuzens
 *
 */
@Documented( name="upgradeAction", description="Response to an application request for an upgrade check." )
public class RestUpgradeAction
{
	public enum UpgradeActionType {
		NONE,
		FORCE,
		RECOMMEND
	}
	
	@ApiField( "The recommended action to take for upgrade." )
	public UpgradeActionType upgradeAction;
	@ApiField( "Echo of the device information this upgrade action is for." )
	public DeviceType deviceType;
	@ApiField( "The current version of the app." )
	public String currentVersion;
	@ApiField( "A link to the upgrade package/app store." )
	public String link;
}
