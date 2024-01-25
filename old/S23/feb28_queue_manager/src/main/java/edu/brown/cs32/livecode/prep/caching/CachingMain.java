package edu.brown.cs32.livecode.prep.caching;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CachingMain {

    public static void main(String[] args) {
        try {
          //  demo();
            cachedDemo();
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
        // Search once
        for(String line : fs.searchLines("love")) {
            System.out.println(line);
        }
        // Search twice
        System.out.println(fs.searchLines("love").size());
    }

    static void cachedDemo() throws IOException {
        Searcher fs = new CachedFileSearcher(new FileSearcher("src/main/resources/frankenstein.txt"));

        // Search once
        for(String line : fs.searchLines("love")) {
            System.out.println(line);
        }
        // Search twice
        System.out.println(fs.searchLines("love").size());
    }
}
