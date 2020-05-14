package robtest.stateinterfw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import robtest.stateinterfw.data.IRepository;

@Controller
@RequestMapping("/message-devices")
public class MessageDeviceController {
    private IRepository _repository;

    public MessageDeviceController(IRepository repository) {
        _repository = repository;
    }

    @GetMapping("/index")
    public String index() {
        return "message-devices/index";
    }
}
