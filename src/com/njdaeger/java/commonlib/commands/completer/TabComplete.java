package com.njdaeger.java.commonlib.commands.completer;

public @interface TabComplete {
	
	/**
	 * The completion strings
	 */
	String[] completions();
	
	/**
	 * How many args the command needs in order for this completion.
	 */
	int length();
	
	/**
	 * The previous argument from where the tab complete starts.
	 */
	String previous() default "";
	
}
