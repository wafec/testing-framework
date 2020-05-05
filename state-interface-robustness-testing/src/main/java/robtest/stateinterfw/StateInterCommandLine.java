package robtest.stateinterfw;

import com.google.inject.Inject;
import robtest.stateinterfw.examples.openStack.IOpenStackCommandLine;

import java.util.Arrays;

public class StateInterCommandLine implements IStateInterCommandLine {
    private IOpenStackCommandLine _openStackCommandLine;

    @Inject
    public StateInterCommandLine(IOpenStackCommandLine openStackCommandLine) {
        this._openStackCommandLine = openStackCommandLine;
    }

    @Override
    public void run(String... args) {
        String example = args[0];
        String[] mArgs = null;
        if (args.length - 1 > 0) {
            mArgs = Arrays.copyOfRange(args, 1, args.length);
        }
        switch (example) {
            case "openStack":
                this._openStackCommandLine.run(mArgs);
                break;
        }
    }
}
