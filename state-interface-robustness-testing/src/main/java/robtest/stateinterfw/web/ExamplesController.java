package robtest.stateinterfw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/examples")
public class ExamplesController {
    public ExamplesController() {

    }

    @GetMapping("/index")
    public String index() {
        return "examples/index";
    }
}
