package edu.brown.cs32.livecode.search;

import java.util.List;

/** This is a placeholder for our toy example. See the notes. Anything that
 * implements such a method can be a datasource. (In general, the return type
 * may be too specific -- we can do better -- but for today, and for 1.2, it suffices. */
public interface Datasource {
    List<List<String>> entries();
}
