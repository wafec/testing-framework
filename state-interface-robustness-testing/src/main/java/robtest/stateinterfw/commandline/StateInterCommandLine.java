package robtest.stateinterfw.commandline;

import com.google.inject.Inject;
import robtest.stateinterfw.examples.openStack.IOpenStackCommandLine;
import robtest.stateinterfw.files.commandline.IFileCommandLine;
import robtest.stateinterfw.virtualbox.commandline.IVirtualBoxCommandLine;
import robtest.stateinterfw.web.StateInterWebApplication;

import java.util.Arrays;

public class StateInterCommandLine implements IStateInterCommandLine {
    private IOpenStackCommandLine _openStackCommandLine;
    private IFileCommandLine _fileCommandLine;
    private IVirtualBoxCommandLine _virtualBoxCommandLine;

    @Inject
    public StateInterCommandLine(IOpenStackCommandLine openStackCommandLine,
                                 IFileCommandLine fileCommandLine,
                                 IVirtualBoxCommandLine virtualBoxCommandLine) {
        this._openStackCommandLine = openStackCommandLine;
        this._fileCommandLine = fileCommandLine;
        this._virtualBoxCommandLine = virtualBoxCommandLine;
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
            case "testCase":
                this._fileCommandLine.run(mArgs);
                break;
            case "web":
                StateInterWebApplication.run();
                break;
            case "virtualbox":
                this._virtualBoxCommandLine.run(mArgs);
                break;
        }
    }
}
