package robtest.stateinterfw.web;

import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.State;
import robtest.stateinterfw.web.dozer.CustomDozerFactory;
import robtest.stateinterfw.web.models.StateCreateRequestModel;

import java.util.List;

@Controller
@RequestMapping("/states")
public class StateController {
    private IRepository repository;
    private Mapper mapper;

    public StateController(IRepository repository) {
        this.repository = repository;
        this.mapper = CustomDozerFactory.buildWeb();
    }

    @GetMapping("/index")
    public String index() {
        return "states/index";
    }

    @GetMapping("/create")
    public String create() {
        return "states/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute StateCreateRequestModel createModel) {
        State state = mapper.map(createModel, State.class);
        repository.save(state);
        return "/states/result";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<State> stateList = repository.query("from State", State.class);
        model.addAttribute("stateList", stateList);
        return "/states/list";
    }
}
