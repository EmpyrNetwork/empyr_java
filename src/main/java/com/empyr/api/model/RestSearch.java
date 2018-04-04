/**
 * 
 */
package com.empyr.api.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

/**
 * @author jcuzens
 *
 */
@Documented( name="searchResults", description="A container for search results. Search results differ from the" +
		" standard results by having the addition of a facetMap which allows further filtering." )
public class RestSearch<T> extends RestBase
{
	@ApiField( "A map of facets that can be used to facilitate filtering on the results." )
	public Map<String,Collection> facetMap = new HashMap<String,Collection>();
	@ApiField( "A collection of the actual underlying results objects. In other words, the actual search results." )
	public Collection<T> results;
	@ApiField( "The number of hits in the results container." )
	public int hits;
}
