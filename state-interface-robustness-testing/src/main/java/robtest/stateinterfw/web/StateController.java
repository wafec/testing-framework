package robtest.stateinterfw.web;

import com.github.dozermapper.core.Mapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

    @GetMapping({"/list", "/list/{id}"})
    public String list(Model model, @PathVariable(required = false) Integer id) {
        model.addAttribute("id", ObjectUtils.firstNonNull(id, -1));
        model.addAttribute("name", "");
        model.addAttribute("timeout", "");
        model.addAttribute("required", false);
        if (id != null) {
            State state = repository.get(id, State.class);
            model.addAttribute("name", state.getName());
            model.addAttribute("timeout", state.getTimeout());
            model.addAttribute("required", state.isRequired());
        }
        List<State> stateList = repository.query("from State", State.class);
        model.addAttribute("stateList", stateList);
        return "/states/list";
    }

    @PostMapping("/add")
    public ModelAndView add(@ModelAttribute StateCreateRequestModel createModel) {
        switch (createModel.getOperation()) {
            case "post": addPost(createModel); break;
            case "put": addPut(createModel); break;
        }
        return new ModelAndView("redirect:/states/list");
    }

    private void addPost(StateCreateRequestModel model) {
        State state = new State();
        state.setName(model.getName());
        state.setTimeout(model.getTimeout());
        state.setRequired(model.isRequired());
        repository.save(state);
    }

    private void addPut(StateCreateRequestModel model) {
        State state = repository.get(model.getId(), State.class);
        state.setName(model.getName());
        state.setTimeout(model.getTimeout());
        state.setRequired(model.isRequired());
        repository.update(state);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id) {
        State state = repository.get(id, State.class);
        repository.remove(state);
        return new ModelAndView("redirect:/states/list");
    }
}
