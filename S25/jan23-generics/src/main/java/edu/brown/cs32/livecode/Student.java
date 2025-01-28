package edu.brown.cs32.livecode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a Javadoc comment (notice the double asterisk on the first line).
 * It will power mouseover information and documentation, usually automatically.
 * To do this, it has some annotations, like @return (see below).
 *
 * *ALL* public-facing methods should contain an informative Javadoc. Why? Because
 * they're (hopefully) meant to be used by other engineers, not just you! Public
 * classes should also get Javadoc (I've omitted it for space.)
 *
 * @return the most common to-do item
 */

public class Student<T> {
    List<T> todos;

    public Student(List<T> todos) {
        this.todos = todos;
    }

    public T mostCommonTodoItem() {
        if(this.todos.isEmpty()) {
            throw new IllegalStateException();
        }
        Map<T, Integer> counts = new HashMap<>();
        for(T item : this.todos) {
            if(!counts.containsKey(item)) counts.put(item, 1);
            else counts.put(item, counts.get(item) + 1);
        }
        T mostCommonItem = null;
        int howCommon = 0;
        for(T item : counts.keySet()) {
            if(counts.get(item) > howCommon) {
                mostCommonItem = item;
                howCommon = counts.get(item);
            }
        }
        return mostCommonItem;
    }

}

