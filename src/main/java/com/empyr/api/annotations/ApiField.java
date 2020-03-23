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
public @interface ApiField 
{
	String value();
	String name() default "";
	String[] values() default {};
	Class<?>[] type() default {};
	boolean required() default true;
	boolean nullable() default false;
	boolean optional() default false;
}
