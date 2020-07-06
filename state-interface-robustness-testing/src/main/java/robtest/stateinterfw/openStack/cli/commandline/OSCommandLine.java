package robtest.stateinterfw.openStack.cli.commandline;

import org.apache.commons.cli.*;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import robtest.core.ConditionalWaiter;
import robtest.core.commandline.AbstractCommandLine;
import robtest.stateinterfw.openStack.cli.FlavorClient;
import robtest.stateinterfw.openStack.cli.ImageClient;
import robtest.stateinterfw.openStack.cli.ServerClient;
import robtest.stateinterfw.openStack.cli.VolumeClient;
import robtest.stateinterfw.openStack.cli.waiters.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class OSCommandLine extends AbstractCommandLine implements IOSCommandLine {
    public OSCommandLine() {
        add("test", this::test);
        add("server-create", this::serverCreate);
        add("server-details", this::serverDetails);
        add("server-delete", this::serverDelete);
        add("server-resize", this::serverResize);
        add("server-resize-confirm", this::serverConfirmResize);
        add("server-stop", this::serverStop);
        add("server-start", this::serverStart);
        add("server-shelve", this::serverShelve);
        add("server-unshelve", this::serverUnshelve);
        add("server-pause", this::serverPause);
        add("server-unpause", this::serverUnpause);
        add("server-rebuild", this::serverRebuild);
        add("server-migrate", this::serverMigrate);
        add("server-live-migrate", this::serverLiveMigrate);
        add("volume-list", this::volumeList);
        add("volume-create", this::volumeCreate);
        add("volume-details", this::volumeDetails);
        add("volume-delete", this::volumeDelete);
        add("volume-extend", this::volumeExtend);
        add("volume-attach", this::volumeAttach);
        add("volume-detach", this::volumeDetach);
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

    protected void serverCreate(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("name").hasArg().build());
        options.addOption(Option.builder().longOpt("image").hasArg().build());
        options.addOption(Option.builder().longOpt("flavor").hasArg().build());
        options.addOption(Option.builder().longOpt("network").hasArg().build());
        ServerClient serverCli = new ServerClient();
        try {
            CommandLineParser parser = new DefaultParser();
            var commandLine = parser.parse(options, args);
            var createdServer = serverCli.createServer(
                    Integer.parseInt(commandLine.getOptionValue("test_id")),
                    commandLine.getOptionValue("name"),
                    commandLine.getOptionValue("image"),
                    commandLine.getOptionValue("flavor"),
                    commandLine.getOptionValue("network"));
            if (createdServer != null) {
                var result = ConditionalWaiter.waitFor(() -> {
                    var server = serverCli.serverDetails(Integer.parseInt(commandLine.getOptionValue("test_id")), createdServer.getName());
                    if (server != null && !server.getStatus().toLowerCase().equals("error")) {
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
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void serverDetails(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("server_name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var serverName = commandLine.getOptionValue("server_name");
            ServerClient serverCli = new ServerClient();
            var result = serverCli.serverDetails(testId, serverName);
            System.out.println(result.toString());
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void serverDelete(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("server_name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var serverName = commandLine.getOptionValue("server_name");
            ServerClient serverCli = new ServerClient();
            serverCli.serverDelete(testId, serverName);
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void serverResize(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("server_name").hasArg().build());
        options.addOption(Option.builder().longOpt("flavor_name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var serverName = commandLine.getOptionValue("server_name");
            var flavor = commandLine.getOptionValue("flavor_name");
            ServerClient serverCli = new ServerClient();
            var server = serverCli.serverDetails(testId, serverName);
            System.out.println(server.toString());
            serverCli.serverResize(testId, serverName, flavor);
            var result = new ConfirmOrRejectResizeWaiter(testId, serverName).waitCondition();
            System.out.println(result.toString());
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void serverConfirmResize(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("server_name").hasArg().build());
        options.addOption(Option.builder().longOpt("flavor_name").hasArg().build());
        OptionGroup confirmReject = new OptionGroup();
        confirmReject.addOption(Option.builder().longOpt("confirm").build());
        confirmReject.addOption(Option.builder().longOpt("reject").build());
        options.addOptionGroup(confirmReject);
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var server = commandLine.getOptionValue("server_name");
            var flavor = commandLine.getOptionValue("flavor_name");
            boolean confirm = commandLine.hasOption("confirm");
            ServerClient serverCli = new ServerClient();
            serverCli.serverConfirmOrRejectResize(testId, server, confirm);
            new ConfirmedOrRejectedResizeWaiter(testId, server, flavor).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void serverStop(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("server_name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var server = commandLine.getOptionValue("server_name");
            ServerClient serverCli = new ServerClient();
            serverCli.serverStop(testId, server);
            new StopWaiter(testId, server).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void serverStart(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("server_name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var serverName = commandLine.getOptionValue("server_name");
            var serverClient = new ServerClient();
            serverClient.serverStart(testId, serverName);
            new PowerOnWaiter(testId, serverName).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void serverShelve(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("server_name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var serverName = commandLine.getOptionValue("server_name");
            var serverClient = new ServerClient();
            serverClient.serverShelve(testId, serverName);
            new ShelveWaiter(testId, serverName).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void serverUnshelve(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("server_name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var serverName = commandLine.getOptionValue("server_name");
            ServerClient serverClient = new ServerClient();
            serverClient.serverUnshelve(testId, serverName);
            new PowerOnWaiter(testId, serverName).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void serverPause(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("server_name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var serverName = commandLine.getOptionValue("server_name");
            ServerClient serverClient = new ServerClient();
            serverClient.serverPause(testId, serverName);
            new PauseWaiter(testId, serverName).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void serverUnpause(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("server_name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var serverName = commandLine.getOptionValue("server_name");
            ServerClient serverClient = new ServerClient();
            serverClient.serverUnpause(testId, serverName);
            new PowerOnWaiter(testId, serverName).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void serverRebuild(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("server_name").hasArg().build());
        options.addOption(Option.builder().longOpt("image_name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var serverName = commandLine.getOptionValue("server_name");
            var imageName = commandLine.getOptionValue("image_name");
            ServerClient serverClient = new ServerClient();
            serverClient.serverRebuild(testId, serverName, imageName);
            new RebuildWaiter(testId, serverName).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void serverMigrate(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("server_name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var serverName = commandLine.getOptionValue("server_name");
            ServerClient serverClient = new ServerClient();
            serverClient.serverMigrate(testId, serverName);
            new MigrateWaiter(testId, serverName).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void serverLiveMigrate(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("server_name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var serverName = commandLine.getOptionValue("server_name");
            ServerClient serverClient = new ServerClient();
            serverClient.serverLiveMigrate(testId, serverName);
            new LiveMigrateWaiter(testId, serverName).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void volumeList(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            VolumeClient volumeClient = new VolumeClient();
            var result = volumeClient.volumeList(testId);
            if (result != null) {
                for (var storage : result) {
                    System.out.println(storage.toString());
                }
            }
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void volumeCreate(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("name").hasArg().build());
        options.addOption(Option.builder().longOpt("size").hasArg().build());
        options.addOption(Option.builder().longOpt("zone").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var name = commandLine.getOptionValue("name");
            var size = Integer.parseInt(commandLine.getOptionValue("size"));
            var zone = commandLine.getOptionValue("zone");
            VolumeClient volumeClient = new VolumeClient();
            var result = volumeClient.volumeCreate(testId, name, zone, size);
            if (result != null) {
                System.out.println(result.toString());
                new VolumeCreateWaiter(testId, name).waitCondition();
            }
            else
                System.out.println("Volume not created");
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void volumeDetails(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var name = commandLine.getOptionValue("name");
            VolumeClient volumeClient = new VolumeClient();
            var volume = volumeClient.volumeDetails(testId, name);
            if (volume != null) {
                System.out.println(volume.toString());
            } else {
                System.out.println("Volume not found");
            }
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void volumeDelete(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var name = commandLine.getOptionValue("name");
            VolumeClient volumeClient = new VolumeClient();
            volumeClient.volumeDelete(testId, name);
            new VolumeDeleteWaiter(testId, name).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void volumeExtend(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("name").hasArg().build());
        options.addOption(Option.builder().longOpt("size").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var name = commandLine.getOptionValue("name");
            var size = Integer.parseInt(commandLine.getOptionValue("size"));
            VolumeClient volumeClient = new VolumeClient();
            volumeClient.volumeExtend(testId, name, size);
            new VolumeExtendWaiter(testId, name).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void volumeAttach(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("name").hasArg().build());
        options.addOption(Option.builder().longOpt("server").hasArg().build());
        options.addOption(Option.builder().longOpt("mountpoint").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var name = commandLine.getOptionValue("name");
            var server = commandLine.getOptionValue("server");
            var mountpoint = commandLine.getOptionValue("mountpoint");
            VolumeClient volumeClient = new VolumeClient();
            volumeClient.volumeAttach(testId, name, server, mountpoint);
            new VolumeAttachWaiter(testId, name).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    protected void volumeDetach(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("test_id").hasArg().build());
        options.addOption(Option.builder().longOpt("name").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var testId = Integer.parseInt(commandLine.getOptionValue("test_id"));
            var name = commandLine.getOptionValue("name");
            VolumeClient volumeClient = new VolumeClient();
            volumeClient.volumeDetach(testId, name);
            new VolumeDetachWaiter(testId, name).waitCondition();
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void run(String... args) {
        parse(args);
    }
}
