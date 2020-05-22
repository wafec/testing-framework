package robtest.stateinterfw.web;

import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.TestPlan;
import robtest.stateinterfw.examples.openStack.OpenStackTestPlan;
import robtest.stateinterfw.web.dozer.CustomDozerFactory;
import robtest.stateinterfw.web.models.TestPlanCreateRequestModel;

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
        return "examples/openstach/index";
    }

    @GetMapping("/list")
    public String list() {
        return "examples/openstack/list";
    }
}
