package robtest.stateinterfw.examples.openStack;

import robtest.stateinterfw.*;
import robtest.stateinterfw.examples.openStack.content.OpenStackUserContent;

public class OpenStackTestInputCommand extends TestInputCommand implements ITestInputCommand {
    public OpenStackTestInputCommand() {
        add("identity.login", this::login);
    }

    private OpenStackUserContent getUserContent() {
        if (this.testExecutionContext != null) {
            if (this.testExecutionContext.getVolatileUserContent() != null &&
                this.testExecutionContext.getVolatileUserContent() instanceof OpenStackUserContent) {
                return (OpenStackUserContent) this.testExecutionContext.getVolatileUserContent();
            } else if (this.testExecutionContext.getVolatileUserContent() != null) {
                throw new IllegalArgumentException("User content is not an instance of OpenStackUserContent");
            } else {
                var userContent = new OpenStackUserContent();
                this.testExecutionContext.setVolatileUserContent(userContent);
                return userContent;
            }
        }
        return null;
    }

    private Object login(ITestInputArgs args) {
        var userContent = getUserContent();
        if (userContent != null) {
            String user = args.get("user").getDataValue();
            String password = args.get("password").getDataValue();
            String domain = args.get("domain").getDataValue();
            String endpoint = args.get("endpoint").getDataValue();
            String project = args.get("project").getDataValue();
            userContent.getLogin().setUser(user);
            userContent.getLogin().setPassword(password);
            userContent.getLogin().setDomain(domain);
            userContent.getLogin().setEndpoint(endpoint);
            userContent.getLogin().setProject(project);
        }
        return null;
    }
}
