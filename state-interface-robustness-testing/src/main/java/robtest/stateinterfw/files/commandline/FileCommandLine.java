package robtest.stateinterfw.files.commandline;

import com.google.inject.Inject;
import org.apache.commons.cli.*;
import robtest.stateinterfw.files.IFileTestCase;
import robtest.stateinterfw.files.ITestCaseLoader;

public class FileCommandLine implements IFileCommandLine {
    private ITestCaseLoader _testCaseLoader;

    @Inject
    public FileCommandLine(ITestCaseLoader testCaseLoader) {
        _testCaseLoader = testCaseLoader;
    }

    @Override
    public void run(String... args) {
        Options options = new Options();
        OptionGroup saveOpts = new OptionGroup();
        saveOpts.addOption(Option.builder()
                .longOpt("save")
                .required()
        .build());
        options.addOptionGroup(saveOpts);
        options.addOption(Option.builder()
                .longOpt("file")
                .required()
                .hasArg()
        .build());
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            if (commandLine.hasOption("save")) {
                IFileTestCase fileTestCase = _testCaseLoader.load(commandLine.getOptionValue("file"));
                _testCaseLoader.add(fileTestCase);
            }
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }
}
