package robtest.stateinterfw.virtualbox.commandline;

import com.google.inject.Inject;
import org.apache.commons.cli.*;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.virtualbox.IVirtualBoxManageClient;
import robtest.stateinterfw.virtualbox.VirtualBoxEnvironment;

public class VirtualBoxCommandLine implements IVirtualBoxCommandLine {
    private IRepository repository;
    private IVirtualBoxManageClient virtualBoxClient;

    @Inject
    public VirtualBoxCommandLine(IRepository repository, IVirtualBoxManageClient virtualBoxClient) {
        this.repository = repository;
        this.virtualBoxClient = virtualBoxClient;
    }

    @Override
    public void run(String... args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("id").hasArg().required().build());
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            if (commandLine.hasOption("id")) {
                testVirtualBoxEnvironment(Integer.parseInt(commandLine.getOptionValue("id")));
            }
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    private void testVirtualBoxEnvironment(int id) {
        VirtualBoxEnvironment environment = repository.get(id, VirtualBoxEnvironment.class);
        virtualBoxClient.powerOff(environment);
        virtualBoxClient.snapshot(environment);
        virtualBoxClient.powerOn(environment);
        virtualBoxClient.powerOff(environment);
    }
}
