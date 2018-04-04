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
public @interface Documented 
{
	String name() default "";

	String description();
	
	Class<?>[] parent() default {};

	Class<?>[] returns() default {};
}
