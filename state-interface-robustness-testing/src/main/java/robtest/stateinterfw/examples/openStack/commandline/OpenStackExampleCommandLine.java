package robtest.stateinterfw.examples.openStack.commandline;

import com.google.inject.Inject;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import robtest.core.commandline.AbstractCommandLine;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.examples.openStack.IOpenStackTestManager;
import robtest.stateinterfw.examples.openStack.OpenStackTestPlan;

public class OpenStackExampleCommandLine extends AbstractCommandLine implements IOpenStackExampleCommandLine {
    private IOpenStackTestManager _testManager;
    private IRepository _repository;

    @Inject
    public OpenStackExampleCommandLine(IOpenStackTestManager testManager, IRepository repository) {
        this._testManager = testManager;
        this._repository = repository;
    }

    @Override
    public void run(String... args) {
        add("golden-run", this::testGoldenRun);
    }

    protected void testGoldenRun(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("plan").hasArg().build());
        var commandLineParser = new DefaultParser();
        try {
            var commandLine = commandLineParser.parse(options, args);
            var plan = Integer.parseInt(commandLine.getOptionValue("plan"));
            OpenStackTestPlan testPlan = _repository.get(plan, OpenStackTestPlan.class);

        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }
}
