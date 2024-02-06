package edu.brown.cs32.livecode.caching;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * You can have multiple "main" classes in a project. Can you control
 * which runs in IntelliJ by clicking the appropriate play button
 * or by editing your run configuration(s). From the command line,
 * you can use something like the `run` script we gave you in the
 * CSV stencil. Notice that it references a class name to run!
 */
public class CachingMain {

    public static void main(String[] args) {
        try {
            // run without cache
            demo(new FileSearcher("src/main/resources/frankenstein.txt"));
            // run with cache
            demo(new CachedFileSearcher(new FileSearcher("src/main/resources/frankenstein.txt")));

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch(IOException e) {
            System.out.println("Error reading file.");
        }
    }

    /**
     * Search for "love" in the book text, and then *again*
     * Can we avoid doing the work twice?
     * @throws IOException if there is trouble reading the file
     */
    static void demo(Searcher<String,String> fs) throws IOException {
        // Search once
        for(String line : fs.search("love")) {
            System.out.println(line);
        }
        // Search twice
        System.out.println(fs.search("love").size());
    }

}
