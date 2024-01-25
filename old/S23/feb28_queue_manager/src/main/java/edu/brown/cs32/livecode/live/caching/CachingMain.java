package edu.brown.cs32.livecode.live.caching;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CachingMain {

    public static void main(String[] args) {
        try {
            demo();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch(IOException e) {
            System.out.println("Error reading file.");
        }
    }
    /**
     * Use unproxied FileSearcher to search *twice*
     * @throws IOException
     */
    static void demo() throws IOException {
        Searcher fs = new FileSearcher("src/main/resources/frankenstein.txt");
        Searcher ps = new SearcherProxy(fs);
        // Search once
        for(String line : ps.searchLines("love")) {
            System.out.println(line);
        }
        // Search twice
        System.out.println(ps.searchLines("love").size());
    }
}
