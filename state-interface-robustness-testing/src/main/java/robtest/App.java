/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package robtest;

import com.google.inject.Guice;
import com.google.inject.Injector;
import robtest.stateinterfw.IStarter;
import robtest.stateinterfw.guice.DatabaseModule;
import robtest.stateinterfw.guice.EnvironmentModule;
import robtest.stateinterfw.guice.FrameworkModule;
import robtest.stateinterfw.guice.MapperModule;

public class App {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(
                new MapperModule(),
                new DatabaseModule(),
                new FrameworkModule(),
                new EnvironmentModule()
        );
        IStarter starter = injector.getInstance(IStarter.class);
        starter.start();
    }
}
