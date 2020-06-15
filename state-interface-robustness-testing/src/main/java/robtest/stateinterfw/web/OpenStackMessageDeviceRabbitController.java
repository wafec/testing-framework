package robtest.stateinterfw.web;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.Param;
import robtest.stateinterfw.examples.openStack.OpenStackTestPlan;
import robtest.stateinterfw.rabbit.RabbitMessageDevice;
import robtest.stateinterfw.web.models.MessageDeviceRabbitAddDeviceRequestModel;
import robtest.stateinterfw.web.models.MessageDeviceRabbitAddRequestModel;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/examples/openstack/{id}/message-device/rabbit")
public class OpenStackMessageDeviceRabbitController {
    private IRepository repository;

    public OpenStackMessageDeviceRabbitController(IRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/add")
    public String add(@PathVariable int id, Model model, @RequestParam(required = false) String filter) {
        List<RabbitMessageDevice> messageDeviceList = repository.query("from RabbitMessageDevice where (:filter is null or (url like :filter or user like :filter or password like :filter))",
                RabbitMessageDevice.class, Param.list("filter", filter == null ? null : String.format("%%%s%%", filter)));
        OpenStackTestPlan testPlan = repository.get(id, OpenStackTestPlan.class);
        messageDeviceList = messageDeviceList.stream().filter(md -> testPlan.getMessageDevices().stream().noneMatch(t -> t.getId() == md.getId())).collect(Collectors.toList());
        model.addAttribute("id", id);
        model.addAttribute("filter", ObjectUtils.firstNonNull(filter, ""));
        model.addAttribute("messageDeviceList", messageDeviceList);
        return "/examples/openstack/message-device/rabbit/message-device-add";
    }

    @PostMapping("/add/device")
    public ModelAndView add(@PathVariable int id, @ModelAttribute MessageDeviceRabbitAddDeviceRequestModel addModel, Model model) {
        model.addAttribute("id", id);
        OpenStackTestPlan testPlan = repository.querySingleResult("from OpenStackTestPlan where id = :id",
                OpenStackTestPlan.class, Param.list("id", id).all());
        RabbitMessageDevice messageDevice = repository.querySingleResult("from RabbitMessageDevice where id = :id",
                RabbitMessageDevice.class, Param.list("id", addModel.getMessageDeviceId()).all());
        testPlan.getMessageDevices().add(messageDevice);
        repository.update(testPlan);
        return new ModelAndView(String.format("redirect:/examples/openstack/%d/message-device/rabbit/add", id));
    }
}
