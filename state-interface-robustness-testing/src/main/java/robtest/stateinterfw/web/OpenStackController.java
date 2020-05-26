package robtest.stateinterfw.web;

import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.Param;
import robtest.stateinterfw.data.TestCase;
import robtest.stateinterfw.data.TestPlan;
import robtest.stateinterfw.examples.openStack.OpenStackTestPlan;
import robtest.stateinterfw.web.dozer.CustomDozerFactory;
import robtest.stateinterfw.web.models.OpenStackTestCaseAddItemRequestModel;
import robtest.stateinterfw.web.models.OpenStackTestCaseListRequestModel;
import robtest.stateinterfw.web.models.TestPlanCreateRequestModel;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/examples/openstack")
public class OpenStackController {
    private Mapper mapper;
    private IRepository repository;

    public OpenStackController(IRepository repository) {
        this.mapper = CustomDozerFactory.buildWeb();
        this.repository = repository;
    }

    @GetMapping("/create")
    public String create() {
        return "examples/openstack/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute TestPlanCreateRequestModel createModel, Model model) {
        TestPlan testPlan = mapper.map(createModel, OpenStackTestPlan.class);
        repository.save(testPlan);
        model.addAttribute("testPlan", testPlan);
        return "examples/openstack/result";
    }

    @GetMapping("/index")
    public String index() {
        return "examples/openstack/index";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<OpenStackTestPlan> planList = repository.query("from OpenStackTestPlan", OpenStackTestPlan.class);
        model.addAttribute("planList", planList);
        return "examples/openstack/list";
    }

    @GetMapping("/{id}/message-device/list")
    public String listMessageDevice(@PathVariable int id, Model model) {
        OpenStackTestPlan testPlan = repository.querySingleResult("from OpenStackTestPlan where id = :id", OpenStackTestPlan.class,
                Param.list("id", id).all());
        model.addAttribute("id", id);
        model.addAttribute("messageDeviceList", new ArrayList<>(testPlan.getMessageDevices()));
        return "examples/openstack/message-device-list";
    }

    @GetMapping("/{id}/environment/list")
    public String listEnvironment(@PathVariable int id, Model model) {
        OpenStackTestPlan testPlan = repository.querySingleResult("from OpenStackTestPlan where id = :id", OpenStackTestPlan.class,
                Param.list("id", id).all());
        model.addAttribute("id", id);
        model.addAttribute("environmentList", new ArrayList<>(testPlan.getEnvironments()));
        return "examples/openstack/environment-list";
    }

    @GetMapping("/{id}/test-case/list")
    public String listTestCase(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        OpenStackTestPlan testPlan = repository.get(id, OpenStackTestPlan.class);
        model.addAttribute("testCaseList", new ArrayList<>(testPlan.getTestCases()));
        return "examples/openstack/test-case-list";
    }

    @PostMapping("/{id}/test-case/list")
    public String listTestCase(@PathVariable int id, Model model, @ModelAttribute OpenStackTestCaseListRequestModel filterModel) {
        model.addAttribute("id", id);
        OpenStackTestPlan testPlan = repository.get(id, OpenStackTestPlan.class);
        model.addAttribute("testCaseList", new ArrayList<>(testPlan.getTestCases()).stream().filter(tc -> tc.getUniqueIdentifier().contains(filterModel.getFilter())));
        return "examples/openstack/test-case-list";
    }

    @GetMapping("/{id}/test-case/add")
    public String addTestCase(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        List<TestCase> testCase = repository.query("from TestCase", TestCase.class);
        model.addAttribute("testCaseList", testCase);
        return "examples/openstack/test-case-add";
    }

    @PostMapping("/{id}/test-case/add/item")
    public String addTestCase (@PathVariable int id, Model model, OpenStackTestCaseAddItemRequestModel addModel) {
        model.addAttribute("id", id);
        OpenStackTestPlan testPlan = repository.get(id, OpenStackTestPlan.class);
        TestCase testCase = repository.get(addModel.getTestCaseId(), TestCase.class);
        testPlan.getTestCases().add(testCase);
        repository.update(testPlan);
        return "examples/openstack/test-case-result";
    }
}
