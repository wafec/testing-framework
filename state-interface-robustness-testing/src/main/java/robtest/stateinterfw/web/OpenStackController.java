package robtest.stateinterfw.web;

import com.github.dozermapper.core.Mapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import robtest.stateinterfw.data.*;
import robtest.stateinterfw.examples.openStack.OpenStackTestPlan;
import robtest.stateinterfw.web.dozer.CustomDozerFactory;
import robtest.stateinterfw.web.models.OpenStackTestCaseAddItemRequestModel;
import robtest.stateinterfw.web.models.OpenStackTestCaseListRequestModel;
import robtest.stateinterfw.web.models.TestPlanCreateRequestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping({"/list", "/list/{planId}"})
    public String list(Model model, @PathVariable(required = false) Integer planId) {
        model.addAttribute("planId", ObjectUtils.firstNonNull(planId, -1));
        model.addAttribute("name", "");
        if (planId != null) {
            OpenStackTestPlan testPlan = repository.get(planId, OpenStackTestPlan.class);
            model.addAttribute("name", testPlan.getName());
        }
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
    public String addTestCase(@PathVariable int id, Model model, @RequestParam(required = false) String filter) {
        model.addAttribute("id", id);
        List<TestCase> testCase = repository.query("from TestCase where (:filter is null or (uid like :filter))", TestCase.class,
                Param.list("filter", filter == null ? null : String.format("%%%s%%", filter)));
        model.addAttribute("filter", ObjectUtils.firstNonNull(filter, ""));
        OpenStackTestPlan testPlan = repository.get(id, OpenStackTestPlan.class);
        testCase = testCase.stream().filter(t -> testPlan.getTestCases().stream().noneMatch(o -> o.getId() == t.getId())).collect(Collectors.toList());
        model.addAttribute("testCaseList", testCase);
        return "examples/openstack/test-case-add";
    }

    @PostMapping("/{id}/test-case/add/item")
    public ModelAndView addTestCase (@PathVariable int id, Model model, OpenStackTestCaseAddItemRequestModel addModel) {
        model.addAttribute("id", id);
        OpenStackTestPlan testPlan = repository.get(id, OpenStackTestPlan.class);
        TestCase testCase = repository.get(addModel.getTestCaseId(), TestCase.class);
        testPlan.getTestCases().add(testCase);
        repository.update(testPlan);
        return new ModelAndView(String.format("redirect:/examples/openstack/%d/test-case/add", id));
    }

    @PostMapping("/add")
    public ModelAndView add(Model model, TestPlanCreateRequestModel createModel) {
        switch (createModel.getOperation()) {
            case "post": addPost(createModel); break;
            case "put": addPut(createModel); break;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/examples/openstack/list");
        return modelAndView;
    }

    private void addPost(TestPlanCreateRequestModel createModel) {
        OpenStackTestPlan openStackTestPlan = new OpenStackTestPlan();
        openStackTestPlan.setName(createModel.getName());
        repository.save(openStackTestPlan);
    }

    private void addPut(TestPlanCreateRequestModel createModel) {
        OpenStackTestPlan testPlan = repository.get(createModel.getPlanId(), OpenStackTestPlan.class);
        testPlan.setName(createModel.getName());
        repository.update(testPlan);
    }

    @GetMapping("/delete/{planId}")
    private ModelAndView delete(@PathVariable Integer planId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/examples/openstack/list");
        remove(planId, modelAndView);
        return modelAndView;
    }

    private void remove(Integer planId, ModelAndView modelAndView) {
        try (var transactionRepository = ((ITransactionRepositoryFactory)repository).getTransaction()) {
            OpenStackTestPlan testPlan = transactionRepository.get(planId, OpenStackTestPlan.class);
            transactionRepository.remove(testPlan);
        } catch (Exception exc) {
            exc.printStackTrace();
            modelAndView.addObject("errorMsg", exc.getMessage());
        }
    }
}
