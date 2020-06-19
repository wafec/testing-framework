package robtest.stateinterfw.openStack.cli.commandline;

import org.apache.commons.cli.*;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import robtest.core.ConditionalWaiter;
import robtest.core.commandline.AbstractCommandLine;
import robtest.stateinterfw.openStack.cli.FlavorClient;
import robtest.stateinterfw.openStack.cli.ImageClient;
import robtest.stateinterfw.openStack.cli.ServerClient;

import java.io.File;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class OSCommandLine extends AbstractCommandLine implements IOSCommandLine {
    public OSCommandLine() {
        add("test", this::test);
        add("server", this::server);
        add("mserver", this::mtest);
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
            var created = flavorCli.createFlavor(testId, "testf512", 512, 1, 1);
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
            //flavorCli.deleteFlavor(testId, "test");
            ImageClient imageCli = new ImageClient();
            var images = imageCli.listImages(testId);
            for (var image : images) {
                System.out.println(image.toString());
            }
            var image = imageCli.imageCreate(testId, "testcirros", "qcow2", "bare", new File("cirros.img"));
            if (image != null) {
                System.out.println(String.format("Created! %s", image));
            } else {
                System.out.println("Could not create Image!");
            }
            //imageCli.imageDelete(testId, "test");
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void server(String[] args) {
        ServerClient serverCli = new ServerClient();
        var createdServer = serverCli.createServer(1, UUID.randomUUID().toString(), "cirros", "f512", "provider");
        if (createdServer != null) {
            var result = ConditionalWaiter.waitFor(() -> {
                var server = serverCli.serverDetails(1, createdServer.getName());
                if (server != null) {
                    System.out.println(server.toString());
                    if (server.getStatus().toLowerCase().equals("active")) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    throw new RuntimeException();
                }
            }, TimeUnit.MILLISECONDS.convert(2, TimeUnit.MINUTES), 500);
            System.out.print(result.toString());
        } else {
            System.out.println("Error");
        }
    }

    protected void mtest(String[] args) {
        ServerClient serverCli = new ServerClient();
        serverCli.serverDetails(1, "test");
    }

    @Override
    public void run(String... args) {
        parse(args);
    }
}
