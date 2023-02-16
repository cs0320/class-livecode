package edu.brown.cs32.live.repl;

import java.util.List;

// Note: This could be changed to allow lambdas: Function<...>
public interface CommandFunction {
    String run(List<String> args);
}
