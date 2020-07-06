package robtest.stateinterfw.examples.openStack;

import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.examples.openStack.content.OpenStackUserContent;

public class OpenStackTestExecutionContextWrapper {
    private ITestExecutionContext testExecutionContext;

    public OpenStackTestExecutionContextWrapper(ITestExecutionContext context) {
        this.testExecutionContext = context;
    }

    public OpenStackUserContent getUserContent() {
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
        throw new ExceptionInInitializerError();
    }
}
