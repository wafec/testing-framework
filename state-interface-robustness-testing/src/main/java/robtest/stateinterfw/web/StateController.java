package robtest.stateinterfw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/states")
public class StateController {
    @GetMapping("/index")
    public String index() {
        return "states/index";
    }

    @GetMapping("/create")
    public String create() {
        return "states/create";
    }
}
