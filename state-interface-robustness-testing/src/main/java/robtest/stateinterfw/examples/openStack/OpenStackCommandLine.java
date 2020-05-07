package robtest.stateinterfw.examples.openStack;

import com.google.inject.Inject;
import org.apache.commons.cli.*;

public class OpenStackCommandLine implements IOpenStackCommandLine {
    private IOpenStackTestManager _openStackTestManager;

    @Inject
    public OpenStackCommandLine(IOpenStackTestManager openStackTestManager) {
        this._openStackTestManager = openStackTestManager;
    }

    private void startTestManager(long caseId, long specsId) {
        
    }

    @Override
    public void run(String... args) {
        System.out.println("Working");
        Options options = new Options();
        options.addOption(Option.builder()
                .longOpt("case")
                .required()
                .hasArg()
        .build());
        options.addOption(Option.builder()
                .longOpt("specs")
                .required()
                .hasArg()
        .build());
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            String caseOptValue = commandLine.getOptionValue("case");
            String specsOptValue = commandLine.getOptionValue("specs");
            startTestManager(Long.parseLong(caseOptValue), Long.parseLong(specsOptValue));
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }
}
