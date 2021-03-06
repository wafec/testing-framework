package robtest.stateinterfw.examples.openStack;

import com.google.inject.Inject;
import org.apache.commons.cli.*;
import robtest.core.commandline.AbstractCommandLine;
import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.ITestSpecs;
import robtest.stateinterfw.data.*;

public class OpenStackCommandLine extends AbstractCommandLine implements IOpenStackCommandLine {
    private IRepository repository;
    private IOpenStackTestInputCommand openStackTestInputCommand;

    @Inject
    public OpenStackCommandLine(IRepository repository, IOpenStackTestInputCommand openStackTestInputCommand) {
        this.repository = repository;
        this.openStackTestInputCommand = openStackTestInputCommand;
        add("input-command", this::testInputCommand);
    }

    @Override
    public void run(String... args) {
        parse(args);
    }

    private void testInputCommand(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("plan").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            var commandLine = parser.parse(options, args);
            var planId = Long.parseLong(commandLine.getOptionValue("plan"));
            var command = openStackTestInputCommand;
            OpenStackTestPlan testPlan = repository.get((int) planId, OpenStackTestPlan.class);
            TestExecutionContext context = new TestExecutionContext();
            context.setTestCase((TestCase) testPlan.getTestCase(0));
            var opt = repository.query("from TestSpecs where testPlan.id = :id", TestSpecs.class,
                    Param.list("id", testPlan.getId())).stream().findFirst();
            if (opt.isPresent()) {
                var specs = opt.get();
                context.setTestSpecs(specs);
                command.command(context, context.getTestInput());
                context.moveForward();
                command.command(context, context.getTestInput());
            }
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }
}
