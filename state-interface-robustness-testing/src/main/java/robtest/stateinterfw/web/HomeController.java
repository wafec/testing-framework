package robtest.stateinterfw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import robtest.stateinterfw.data.IRepository;

@Controller
public class HomeController {
    private IRepository _repository;

    @Autowired
    public HomeController(IRepository repository) {
        this._repository = repository;
    }

    @GetMapping("/index")
    public String index() {
        System.out.println(String.format("Instance: %s", _repository));
        return "home/index";
    }
}
