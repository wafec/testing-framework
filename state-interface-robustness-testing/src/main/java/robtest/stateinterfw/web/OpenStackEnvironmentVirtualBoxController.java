package robtest.stateinterfw.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.examples.openStack.OpenStackTestPlan;
import robtest.stateinterfw.virtualbox.VirtualBoxEnvironment;
import robtest.stateinterfw.web.models.EnvironmentVirtualBoxAddDeviceRequestModel;
import robtest.stateinterfw.web.models.EnvironmentVirtualBoxAddRequestModel;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/examples/openstack/{id}/environment/virtualbox")
public class OpenStackEnvironmentVirtualBoxController {
    private IRepository repository;

    public OpenStackEnvironmentVirtualBoxController(IRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/add")
    public String add(@PathVariable int id, Model model) {
        List<VirtualBoxEnvironment> environmentList = repository.query("from VirtualBoxEnvironment",
                VirtualBoxEnvironment.class);
        model.addAttribute("id", id);
        model.addAttribute("environmentList", environmentList);
        return "examples/openstack/environment/virtualbox/environment-add";
    }

    @PostMapping("/add")
    public String add(@PathVariable int id, EnvironmentVirtualBoxAddRequestModel addModel, Model model) {

        return "examples/openstack/environment/virtualbox/environment-add";
    }

    @PostMapping("/add/device")
    public String addEnvironment(@PathVariable int id, Model model, EnvironmentVirtualBoxAddDeviceRequestModel addModel) {
        model.addAttribute("id", id);
        OpenStackTestPlan testPlan = repository.get(id, OpenStackTestPlan.class);
        VirtualBoxEnvironment environment = repository.get(addModel.getEnvironmentId(), VirtualBoxEnvironment.class);
        testPlan.getEnvironments().add(environment);
        repository.update(testPlan);
        return "examples/openstack/environment/virtualbox/environment-result";
    }
}
