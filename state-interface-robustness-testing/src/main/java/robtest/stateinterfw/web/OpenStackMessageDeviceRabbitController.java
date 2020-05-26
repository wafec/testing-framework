package robtest.stateinterfw.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.Param;
import robtest.stateinterfw.examples.openStack.OpenStackTestPlan;
import robtest.stateinterfw.rabbit.RabbitMessageDevice;
import robtest.stateinterfw.web.models.MessageDeviceRabbitAddDeviceRequestModel;
import robtest.stateinterfw.web.models.MessageDeviceRabbitAddRequestModel;

import java.util.List;

@Controller
@RequestMapping("/examples/openstack/{id}/message-device/rabbit")
public class OpenStackMessageDeviceRabbitController {
    private IRepository repository;

    public OpenStackMessageDeviceRabbitController(IRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/add")
    public String add(@PathVariable int id, Model model) {
        List<RabbitMessageDevice> messageDeviceList = repository.query("from RabbitMessageDevice",
                RabbitMessageDevice.class);
        model.addAttribute("id", id);
        model.addAttribute("messageDeviceList", messageDeviceList);
        return "/examples/openstack/message-device/rabbit/message-device-add";
    }

    @PostMapping("/add")
    public String add(@PathVariable int id, MessageDeviceRabbitAddRequestModel addModel, Model model) {
        model.addAttribute("id", id);

        return "/examples/openstack/message-device/rabbit/message-device-add";
    }

    @PostMapping("/add/device")
    public String add(@PathVariable int id, @ModelAttribute MessageDeviceRabbitAddDeviceRequestModel addModel, Model model) {
        model.addAttribute("id", id);
        OpenStackTestPlan testPlan = repository.querySingleResult("from OpenStackTestPlan where id = :id",
                OpenStackTestPlan.class, Param.list("id", id).all());
        RabbitMessageDevice messageDevice = repository.querySingleResult("from RabbitMessageDevice where id = :id",
                RabbitMessageDevice.class, Param.list("id", addModel.getMessageDeviceId()).all());
        testPlan.getMessageDevices().add(messageDevice);
        repository.update(testPlan);
        return "/examples/openstack/message-device/rabbit/message-device-result";
    }
}
