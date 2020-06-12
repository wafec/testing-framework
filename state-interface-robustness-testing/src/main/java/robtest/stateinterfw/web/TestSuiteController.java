package robtest.stateinterfw.web;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import robtest.stateinterfw.data.*;
import robtest.stateinterfw.data.views.TestCaseView;
import robtest.stateinterfw.files.IFileTestCase;
import robtest.stateinterfw.files.ITestCaseLoader;
import robtest.stateinterfw.web.models.TestCaseViewModel;
import robtest.stateinterfw.web.models.TestSuiteTestCreateModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping({"/list", "/list/{id}"})
    public String list(Model model, @PathVariable(required = false) Integer id, @RequestParam(required = false) String filter) {
        List result = repository.query("from TestCaseView t where (:filter is null or (t.uid like :filter)) ", TestCaseView.class,
                Param.list("filter", filter == null ? null : String.format("%%%s%%", filter)));
        model.addAttribute("testCaseList", result);
        model.addAttribute("id", ObjectUtils.firstNonNull(id, -1));
        model.addAttribute("name", "");
        model.addAttribute("filter", ObjectUtils.firstNonNull(filter, ""));
        if (id != null) {
            var testCase = repository.get(id, TestCase.class);
            model.addAttribute("name", testCase.getUniqueIdentifier());
        }
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

    @PostMapping("/list/add")
    public ModelAndView add(@ModelAttribute TestSuiteTestCreateModel createModel) {
        switch (createModel.getOperation()) {
            case "post": create(createModel); break;
            case "put": update(createModel); break;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/testsuite/list");
        return modelAndView;
    }

    private void create(TestSuiteTestCreateModel createModel) {
        TestCase testCase = new TestCase();
        testCase.setUniqueIdentifier(createModel.getUid());
        repository.save(testCase);
    }

    private void update(TestSuiteTestCreateModel createModel) {
        TestCase testCase = repository.get(createModel.getId(), TestCase.class);
        testCase.setUniqueIdentifier(createModel.getUid());
        repository.update(testCase);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, Model model) {
        ModelAndView modelAndView = new ModelAndView("redirect:/testsuite/list");
        delete(id);
        return modelAndView;
    }

    private void delete(Integer id) {
        try (var transactionRepository = ((ITransactionRepositoryFactory) repository).getTransaction()) {
            TestCase testCase = transactionRepository.get(id, TestCase.class);
            for (var input : ObjectUtils.firstNonNull(testCase.getTestInputs(), new ArrayList<TestInput>())) {
                for (var arg : ObjectUtils.firstNonNull(input.getTestInputArguments(), new ArrayList<TestInputArgument>())) {
                    transactionRepository.remove(arg);
                }
                for (var state : ObjectUtils.firstNonNull(input.getTestStates(), new ArrayList<TestState>())) {
                    transactionRepository.remove(state);
                }
                for (var message : ObjectUtils.firstNonNull(input.getTestMessages(), new ArrayList<TestMessage>())) {
                    transactionRepository.remove(message);
                }
                transactionRepository.remove(input);
            }
            transactionRepository.remove(testCase);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
