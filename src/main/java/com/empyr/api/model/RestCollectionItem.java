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
@Documented( name="collectionItem", description="A basic container of items." )
public class RestCollectionItem extends RestBase
{
	@ApiField( "The id of this collectionItem." )
	public Integer id;
	@ApiField( "The date this item was added." )
	public Date		dateAdded;
	@ApiField( "The business associated with this item." )
	public RestCompactBusiness business;
}
