package robtest;

import com.google.inject.Inject;
import robtest.stateinterfw.IStateInterCommandLine;

import java.util.Arrays;

public class CommandLine implements ICommandLine {
    private IStateInterCommandLine _stateInterCommandLine;

    @Inject
    public CommandLine(IStateInterCommandLine stateInterCommandLine) {
        this._stateInterCommandLine = stateInterCommandLine;
    }

    @Override
    public void run(String... args) {
        String method = args[0];
        String[] mArgs = null;
        if (args.length - 1 > 0) {
            mArgs = Arrays.copyOfRange(args, 1, args.length);
        }
        switch (method) {
            case "stateinter":
                this._stateInterCommandLine.run(mArgs);
                break;
        }
    }
}
