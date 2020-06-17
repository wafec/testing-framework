package robtest;

import com.google.inject.Inject;
import robtest.os.cli.commandline.IOSCommandLine;
import robtest.stateinterfw.commandline.IStateInterCommandLine;

import java.util.Arrays;

public class CommandLine implements ICommandLine {
    private IStateInterCommandLine _stateInterCommandLine;
    private IOSCommandLine _osCommandLine;

    @Inject
    public CommandLine(IStateInterCommandLine stateInterCommandLine,
                       IOSCommandLine osCommandLine) {
        this._stateInterCommandLine = stateInterCommandLine;
        this._osCommandLine = osCommandLine;
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
            case "oscli":
                this._osCommandLine.run(mArgs);
                break;
        }
    }
}
