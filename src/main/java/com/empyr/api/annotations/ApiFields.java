/**
 * 
 */
package com.empyr.api.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author jcuzens
 *
 */
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ApiFields 
{
	ApiField[] value();
}
