package robtest.stateinterfw.web;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.Param;
import robtest.stateinterfw.examples.openStack.OpenStackTestPlan;
import robtest.stateinterfw.virtualbox.VirtualBoxEnvironment;
import robtest.stateinterfw.web.models.EnvironmentVirtualBoxAddDeviceRequestModel;
import robtest.stateinterfw.web.models.EnvironmentVirtualBoxAddRequestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/examples/openstack/{id}/environment/virtualbox")
public class OpenStackEnvironmentVirtualBoxController {
    private IRepository repository;

    public OpenStackEnvironmentVirtualBoxController(IRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/add")
    public String add(@PathVariable int id, Model model, @RequestParam(required = false) String filter) {
        List<VirtualBoxEnvironment> environmentList = repository.query("from VirtualBoxEnvironment where (:filter is null or (snapshot like :filter or name like :filter))",
                VirtualBoxEnvironment.class, Param.list("filter", filter == null ? null : String.format("%%%s%%", filter)));
        OpenStackTestPlan testPlan = repository.get(id, OpenStackTestPlan.class);
        environmentList = environmentList.stream().filter(e -> testPlan.getEnvironments().stream().noneMatch(o -> o.getId() == e.getId())).collect(Collectors.toList());
        model.addAttribute("id", id);
        model.addAttribute("filter", ObjectUtils.firstNonNull(filter, ""));
        model.addAttribute("environmentList", environmentList);
        return "examples/openstack/environment/virtualbox/environment-add";
    }

    @PostMapping("/add/device")
    public ModelAndView addEnvironment(@PathVariable int id, Model model, EnvironmentVirtualBoxAddDeviceRequestModel addModel) {
        model.addAttribute("id", id);
        OpenStackTestPlan testPlan = repository.get(id, OpenStackTestPlan.class);
        VirtualBoxEnvironment environment = repository.get(addModel.getEnvironmentId(), VirtualBoxEnvironment.class);
        testPlan.getEnvironments().add(environment);
        repository.update(testPlan);
        return new ModelAndView(String.format("redirect:/examples/openstack/%d/environment/virtualbox/add", id));
    }
}
