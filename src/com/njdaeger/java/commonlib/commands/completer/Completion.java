package com.njdaeger.java.commonlib.commands.completer;

public class Completion {
	
	private int length;
	private String[] completions;
	private String previous;
	
	public Completion(int atLength, String... completions) {
		this.length = atLength;
		this.completions = completions;
	}
	
	public Completion(int atLength, String previousArg, String... completions) {
		this.length = atLength;
		this.completions = completions;
		this.previous = previousArg;
	}
	
	/**
	 * Sets the needed previous argument in order for the completions to work.
	 * @param previous The previous needed arg.
	 */
	public void setPrevious(String previous) {
		this.previous = previous;
	}
	
	/**
	 * The completions this command will have.
	 * @param completions The completions to show.
	 */
	public void setCompletions(String... completions) {
		this.completions = completions;
	}
	
	/**
	 * Sets the required length in order for these completions to take place.
	 * @param length The length of the command for these completions to take place.
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Gets the required previous argument in order for these completions to work.
	 * @return The needed previous arg.
	 */
	public String getPrevious() {
		return previous;
	}
	
	/**
	 * Get the length of the command at which these completions will happen.
	 * @return The required length.
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * Gets the possible completions for this command.
	 * @return The list of possible completions.
	 */
	public String[] getCompletions() {
		return completions;
	}
	
}
