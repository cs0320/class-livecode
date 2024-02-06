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
            demo();
           // cachedDemo();
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
        // Note that FileSearcher is not generic, so we don't add any <>
        // The interface, however, is generic and takes 2 type arguments.
        Searcher<String,String> fs = new FileSearcher("src/main/resources/frankenstein.txt");

        // Search once
        for(String line : fs.search("love")) {
            System.out.println(line);
        }
        // Search twice
        System.out.println(fs.search("love").size());
    }

    static void cachedDemo() throws IOException {
        // CachedFileServer is also not generic, so no <>
        Searcher<String,String> fs = new CachedFileSearcher(new FileSearcher("src/main/resources/frankenstein.txt"));

        // Search once
        for(String line : fs.search("love")) {
            System.out.println(line);
        }
        // Search twice
        System.out.println(fs.search("love").size());
    }
}
