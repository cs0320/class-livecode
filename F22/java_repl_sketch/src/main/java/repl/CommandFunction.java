package repl;

import java.util.List;

public interface CommandFunction {
    String run(List<String> args);
}
