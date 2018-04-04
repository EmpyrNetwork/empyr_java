/**
 * 
 */
package com.empyr.api.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jarrodcuzens
 *
 */
public class RestProcessorMapping
{
	public enum Processor {
		AMEX,
		VISA,
		MC
	}
	
	public Processor processor;
	
	public Map<String,String> mapping = new HashMap<String,String>();
}
