package com.njdaeger.java.commonlib.commands.completer;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.njdaeger.java.commonlib.commands.completer.TabComplete.Completor;

@Retention( RetentionPolicy.RUNTIME)
@Repeatable(Completor.class)
public @interface TabComplete {
	
	/**
	 * The completion strings
	 */
	String[] completions() default {};
	
	/**
	 * How many args the command needs in order for this completion.
	 */
	int length() default 0;
	
	/**
	 * The previous argument from where the tab complete starts.
	 */
	String previous() default "";
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Completor {
		
		TabComplete[] value();
	}
	
}
