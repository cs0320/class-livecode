package edu.brown.cs32.livecode.search;

import java.util.List;

/** This is a toy example, and *not* meant to be as complete or well-engineered as your Sprint 1.2.
 I'm using `Datasource` to represent some class that provides the data to search in. It might
 be a `Parser`, but it might be other things too. */
public class Searcher implements Searchable {
    private final Datasource source;
    public Searcher(Datasource datasource) {
        this.source = datasource;
    }

    /** This doesn't do as much as your Sprint 1.2 needs to, but illustrates my point. */
    public boolean search(String content) {
        for(List<String> row : source.entries()) {
            for(String value : row) {
                if(value.equals(content)) return true;
            }
        }
        return false;
    }
}