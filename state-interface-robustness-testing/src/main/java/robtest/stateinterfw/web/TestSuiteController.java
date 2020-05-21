package robtest.stateinterfw.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.files.IFileTestCase;
import robtest.stateinterfw.files.ITestCaseLoader;
import robtest.stateinterfw.web.models.TestCaseViewModel;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/testsuite")
public class TestSuiteController {
    private ITestCaseLoader testCaseLoader;
    private IRepository repository;

    public TestSuiteController(ITestCaseLoader testCaseLoader, IRepository repository) {
        this.testCaseLoader = testCaseLoader;
        this.repository = repository;
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
    public String list(Model model) {
        List result = repository.query(
                "select " +
                        "t.id as id, t.uniqueIdentifier,  count(i.id) as inputCount, count(distinct s.state) as stateCount " +
                        "from " +
                        "TestCase t, TestInput i, TestState s " +
                        "where " +
                        "t.id = i.testCase and i.id = s.testInput " +
                        "group by t.id, t.uniqueIdentifier", new TestCaseViewModel("id", "uid", "inputCount", "stateCount"));
        model.addAttribute("testCaseList", result);
        return "test-suite/list";
    }
}
