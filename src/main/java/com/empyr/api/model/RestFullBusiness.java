/**
 * 
 */
package com.empyr.api.model;

import java.util.Set;

import com.empyr.api.annotations.ApiField;


/**
 * @author jarrodcuzens
 *
 */
public class RestFullBusiness extends RestBusiness
{
	public Set<RestProcessorMapping> processorMapping;
	public String mids;
	
	@ApiField( value="The parent id of the venue.", nullable=true, optional=true)
	public Integer parentId;
}
