package com.njdaeger.java.commonlib.commands.completer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class CompletionBuilder {
	
	private List<Completion> completions = new ArrayList<>();
	
	public CompletionBuilder(Method method) {
		if (method.isAnnotationPresent(TabComplete.class)) {
		    Completion completion;
		    for (TabComplete complete : method.getDeclaredAnnotationsByType(TabComplete.class)) {
		        if (complete.previous() == null) {
                    completion = new Completion(complete.length(), complete.completions());
                    completions.add(completion);
                    System.out.println(completion.getLength());
                    return;
                }
		        completion = new Completion(complete.length(), complete.previous(), complete.completions());
                completions.add(completion);
                System.out.println(completion.getLength());
		        return;
            }
        }
	}
	
    /**
     * Get a list of completions for this command.
     * @return The list of completions.
     */
    public List<Completion> getCompletions() {
        return completions;
    }
    
    /**
     * Set the new list of completions to this command.
     * @param completions A new list of completions.
     */
    public void setCompletions(List<Completion> completions) {
        this.completions = completions;
    }
    
    /**
     * Adds a completion to the list of completions.
     * @param completion The new completion to add.
     */
    public void addCompletion(Completion completion) {
    	this.completions.add(completion);
    }
}
