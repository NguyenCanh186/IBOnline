/**
 * SPInParameter: Annotation of Store Procedure Input Parameter
 * @author GauCon
 *
 */
package com.vmg.ibo.core.config.annoutation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SPInParameter {
	String name() default "";
}
