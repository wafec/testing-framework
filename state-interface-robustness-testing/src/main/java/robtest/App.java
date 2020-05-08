/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package robtest;

import com.google.inject.Guice;
import com.google.inject.Injector;
import robtest.guice.RobTestModule;
import robtest.stateinterfw.data.guice.DataModule;
import robtest.stateinterfw.examples.openStack.guice.OpenStackModule;
import robtest.stateinterfw.faults.languages.python.guice.PythonModule;
import robtest.stateinterfw.files.guice.FilesModule;
import robtest.stateinterfw.guice.StateInterModule;
import robtest.stateinterfw.rabbit.guice.RabbitModule;
import robtest.stateinterfw.virtualbox.guice.VirtualBoxModule;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        Injector injector = getGuiceInjector();
        ICommandLine commandLine = injector.getInstance(ICommandLine.class);
        commandLine.run(Arrays.copyOfRange(args, 0, args.length));
    }

    static Injector getGuiceInjector() {
        return Guice.createInjector(
                new RobTestModule(),
                new StateInterModule(),
                new RabbitModule(),
                new VirtualBoxModule(),
                new OpenStackModule(),
                new DataModule(),
                new PythonModule(),
                new FilesModule()
        );
    }
}
