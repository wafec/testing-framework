package robtest.stateinterfw.os.cli.commandline;

import org.apache.commons.cli.*;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import robtest.core.commandline.AbstractCommandLine;
import robtest.stateinterfw.os.cli.FlavorClient;
import robtest.stateinterfw.os.cli.ImageClient;

import java.io.File;

public class OSCommandLine extends AbstractCommandLine implements IOSCommandLine {
    public OSCommandLine() {
        add("test", this::test);
    }

    protected void test(String[] args) {
        System.out.println("Starting");
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            FlavorClient flavorCli = new FlavorClient();
            var created = flavorCli.createFlavor(testId, "test", 10, 10, 1);
            if (created != null)
                System.out.println(created.toString());
            var flavors = flavorCli.listFlavors(testId);
            if (flavors != null) {
                for (var flavor : flavors) {
                    System.out.println(flavor.toString());
                }
            } else {
                System.out.println("Flavors is null");
            }
            flavorCli.deleteFlavor(testId, "test");
            ImageClient imageCli = new ImageClient();
            var images = imageCli.listImages(testId);
            for (var image : images) {
                System.out.println(image.toString());
            }
            var image = imageCli.imageCreate(testId, "test", "qcow2", "bare", new File("cirros.img"));
            if (image != null) {
                System.out.println(String.format("Created! %s", image));
            } else {
                System.out.println("Could not create Image!");
            }
            imageCli.imageDelete(testId, "test");
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void run(String... args) {
        parse(args);
    }
}
