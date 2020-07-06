package robtest.stateinterfw.files.commandline;

import com.google.inject.Inject;
import org.apache.commons.cli.*;
import robtest.stateinterfw.files.IFileTestCase;
import robtest.stateinterfw.files.ITestCaseLoader;
import robtest.stateinterfw.files.jackson.IJsonTestCaseLoader;
import robtest.stateinterfw.files.text.ITextTestCaseLoader;

public class FileCommandLine implements IFileCommandLine {
    private IJsonTestCaseLoader _testCaseLoader;
    private ITextTestCaseLoader _textTestCaseLoader;

    @Inject
    public FileCommandLine(IJsonTestCaseLoader testCaseLoader, ITextTestCaseLoader textTestCaseLoader) {
        _testCaseLoader = testCaseLoader;
        _textTestCaseLoader = textTestCaseLoader;
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
        OptionGroup typeOpts = new OptionGroup();
        typeOpts.addOption(Option.builder().longOpt("json").build());
        typeOpts.addOption(Option.builder().longOpt("text").build());
        options.addOptionGroup(typeOpts);
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            if (commandLine.hasOption("save")) {
                if (commandLine.hasOption("json")) {
                    IFileTestCase fileTestCase = _testCaseLoader.load(commandLine.getOptionValue("file"));
                    _testCaseLoader.add(fileTestCase);
                } else if (commandLine.hasOption("text")) {
                    IFileTestCase fileTestCase = _textTestCaseLoader.load(commandLine.getOptionValue("file"));
                    _textTestCaseLoader.add(fileTestCase);
                }
            }
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }
}
