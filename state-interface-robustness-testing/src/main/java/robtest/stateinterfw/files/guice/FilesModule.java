package robtest.stateinterfw.files.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.files.ITestCaseLoader;
import robtest.stateinterfw.files.commandline.FileCommandLine;
import robtest.stateinterfw.files.commandline.IFileCommandLine;
import robtest.stateinterfw.files.jackson.TestCaseLoader;
import robtest.stateinterfw.files.mapper.FileMapper;
import robtest.stateinterfw.files.mapper.IFileMapper;

public class FilesModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IFileCommandLine.class).to(FileCommandLine.class);
        bind(ITestCaseLoader.class).to(TestCaseLoader.class);
        bind(IFileMapper.class).to(FileMapper.class);
    }
}
