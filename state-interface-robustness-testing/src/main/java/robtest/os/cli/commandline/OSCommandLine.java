package robtest.os.cli.commandline;

import org.apache.commons.cli.*;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import robtest.core.commandline.AbstractCommandLine;
import robtest.os.cli.FlavorClient;

public class OSCommandLine extends AbstractCommandLine implements IOSCommandLine {
    public OSCommandLine() {
        add("test", this::test);
    }

    protected void test(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            FlavorClient flavorCli = new FlavorClient();
            var flavors = flavorCli.listFlavors(testId);
            if (flavors != null) {
                for (var flavor : flavors) {
                    System.out.println(String.format("Flavor: %s", flavor.getName()));
                }
            } else {
                System.out.println("Flavors is null");
            }
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void run(String... args) {
        parse(args);
    }
}
