package robtest.stateinterfw.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.TestCase;
import robtest.stateinterfw.data.views.TestCaseView;
import robtest.stateinterfw.files.IFileTestCase;
import robtest.stateinterfw.files.ITestCaseLoader;
import robtest.stateinterfw.web.models.TestCaseViewModel;
import robtest.stateinterfw.web.models.TestSuiteTestCreateModel;

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
        List result = repository.query("from TestCaseView", TestCaseView.class);
        model.addAttribute("testCaseList", result);
        return "test-suite/list";
    }

    @GetMapping("/create")
    public String create() {
        return "test-suite/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute TestSuiteTestCreateModel createModel, Model model) {
        TestCase testCase = new TestCase();
        testCase.setUniqueIdentifier(createModel.getUid());
        repository.save(testCase);
        return "test-suite/result";
    }
}
