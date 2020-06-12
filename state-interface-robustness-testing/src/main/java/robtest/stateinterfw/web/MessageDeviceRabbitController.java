package robtest.stateinterfw.web;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.rabbit.RabbitMessageDevice;
import robtest.stateinterfw.web.dozer.CustomDozerFactory;
import robtest.stateinterfw.web.models.RabbitAddRequestModel;
import robtest.stateinterfw.web.models.RabbitCreateRequestModel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/message-devices/rabbit")
public class MessageDeviceRabbitController {
    private IRepository _repository;
    private Mapper _mapper;

    public MessageDeviceRabbitController(IRepository repository) {
        this._repository = repository;
        this._mapper = CustomDozerFactory.buildWeb();
    }

    @GetMapping("/index")
    public String index() {
        return "message-devices/rabbit/index";
    }

    @GetMapping("/create")
    public String create() {
        return "message-devices/rabbit/create";
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute RabbitCreateRequestModel createModel, Model model) {
        RabbitMessageDevice entity = _mapper.map(createModel, RabbitMessageDevice.class);
        _repository.save(entity);
        model.addAttribute("device", entity);
        return new ModelAndView("redirect:/message-devices/rabbit/list");
    }

    @GetMapping({"/list", "/list/{id}"})
    public String list(Model model, @PathVariable(required = false) Integer id, @RequestParam(required = false) String filter) {
        model.addAttribute("id", ObjectUtils.firstNonNull(id, -1));
        model.addAttribute("filter", ObjectUtils.firstNonNull(filter, ""));
        model.addAttribute("host", "");
        model.addAttribute("username", "");
        model.addAttribute("password", "");
        if (id != null) {
            var rabbit = _repository.get(id, RabbitMessageDevice.class);
            model.addAttribute("host", rabbit.getUrl());
            model.addAttribute("username", rabbit.getUser());
            model.addAttribute("password", rabbit.getPassword());
        }
        List<RabbitMessageDevice> messageDeviceList =_repository.query("from RabbitMessageDevice", RabbitMessageDevice.class);
        model.addAttribute("messageDeviceList", messageDeviceList);
        return "message-devices/rabbit/list";
    }

    @PostMapping("/add")
    public ModelAndView add(Model model, @ModelAttribute RabbitAddRequestModel createModel) {
        switch (createModel.getOperation()) {
            case "post": addPost(createModel); break;
            case "put": addPut(createModel); break;
        }
        return new ModelAndView("redirect:/message-devices/rabbit/list");
    }

    private void addPost(RabbitAddRequestModel model) {
        RabbitMessageDevice device = new RabbitMessageDevice();
        device.setUrl(model.getHost());
        device.setPassword(model.getPassword());
        device.setUser(model.getUsername());
        _repository.save(device);
    }

    private void addPut(RabbitAddRequestModel model) {
        RabbitMessageDevice device = _repository.get(model.getId(), RabbitMessageDevice.class);
        device.setUrl(model.getHost());
        device.setPassword(model.getPassword());
        device.setUser(model.getUsername());
        _repository.update(device);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id) {
        var device = _repository.get(id, RabbitMessageDevice.class);
        _repository.remove(device);
        return new ModelAndView("redirect:/message-devices/rabbit/list");
    }
}
