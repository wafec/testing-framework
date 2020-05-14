package robtest.stateinterfw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import robtest.stateinterfw.data.IRepository;

@Controller
public class HomeController {
    private IRepository _repository;

    public HomeController(IRepository repository) {
        this._repository = repository;
        System.out.println(String.format("Instance: %s", _repository));
    }

    @GetMapping("/index")
    public String index() {
        return "home/index";
    }
}
