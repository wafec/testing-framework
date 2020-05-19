package robtest.stateinterfw.web;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.rabbit.RabbitMessageDevice;
import robtest.stateinterfw.web.dozer.CustomDozerFactory;
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
    public String create(@ModelAttribute RabbitCreateRequestModel createModel, Model model) {
        RabbitMessageDevice entity = _mapper.map(createModel, RabbitMessageDevice.class);
        _repository.save(entity);
        model.addAttribute("device", entity);
        return "message-devices/rabbit/result";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<RabbitMessageDevice> messageDeviceList =_repository.query("from RabbitMessageDevice", RabbitMessageDevice.class);
        model.addAttribute("messageDeviceList", messageDeviceList);
        return "message-devices/rabbit/list";
    }
}
