package com.njdaeger.java.commonlib.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Cmd {

	/**
	 * Permission needed to use this command. 
	 * 
	 * @return
	 */
	String[] permissions() default "*";

	/**
	 * The maximum amount of arguments in the command before an error is thrown.
	 * Default is -1 for no maximum amount.
	 * 
	 * @return
	 */
	int max() default -1;

	/**
	 * The minimum amount of arguments in the command before an error is thrown.
	 * Default is -1 for no minimum amount.
	 * 
	 * @return
	 */
	int min() default -1;

	/**
	 * Specifies who is able to do the command. Default is Executor.ALL so
	 * everyone can use the command.
	 * 
	 * @return
	 */
	Executor[] executor() default Executor.ALL;

	/**
	 * The name of the command being executed.
	 * 
	 * @return
	 */
	String name();

	/**
	 * The command description. Default is ""
	 * 
	 * @return
	 */
	String desc();

	/**
	 * The usage of the command. Default is ""
	 * 
	 * @return
	 */
	String usage();

	/**
	 * The command aliases. Default is ""
	 * 
	 * @return
	 */
	String[] aliases() default "";

}
