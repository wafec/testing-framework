package robtest.stateinterfw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import robtest.stateinterfw.files.IFileTestCase;
import robtest.stateinterfw.files.ITestCaseLoader;

import java.io.File;

@Controller
@RequestMapping("/testsuite")
public class TestSuiteController {
    private ITestCaseLoader testCaseLoader;

    public TestSuiteController(ITestCaseLoader testCaseLoader) {
        this.testCaseLoader = testCaseLoader;
    }

    @GetMapping("/index")
    public String index() {
        return "test-suite/index";
    }

    @GetMapping("/importfile")
    public String importFile() {
        return "test-suite/import-file";
    }

    @PostMapping("/importfile")
    public String importFile(@RequestParam("file") MultipartFile file) throws Exception {
        File temp = File.createTempFile("temp", null);
        file.transferTo(temp);
        IFileTestCase fileTestCase = testCaseLoader.load(temp.getAbsolutePath());
        testCaseLoader.add(fileTestCase);
        return "test-suite/import-file";
    }

    @GetMapping("/list")
    public String list() {
        return "test-suite/list";
    }
}
