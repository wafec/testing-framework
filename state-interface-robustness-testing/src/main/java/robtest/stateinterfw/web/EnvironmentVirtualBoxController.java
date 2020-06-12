package robtest.stateinterfw.web;

import com.github.dozermapper.core.Mapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.virtualbox.VirtualBoxEnvironment;
import robtest.stateinterfw.web.dozer.CustomDozerFactory;
import robtest.stateinterfw.web.models.VirtualBoxCreateRequestModel;

import java.util.List;

@Controller
@RequestMapping("/environments/virtualbox")
public class EnvironmentVirtualBoxController {
    private IRepository _repository;
    private Mapper _mapper;

    public EnvironmentVirtualBoxController(IRepository repository) {
        this._repository = repository;
        this._mapper = CustomDozerFactory.buildWeb();
    }

    @GetMapping("/index")
    public String index() {
        return "environments/virtualbox/index";
    }

    @GetMapping("/create")
    public String create() {
        return "environments/virtualbox/create";
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute VirtualBoxCreateRequestModel createModel, Model model) {
        switch (createModel.getOperation()) {
            case "post": create(createModel); break;
            case "put": update(createModel); break;
        }
        return new ModelAndView("redirect:/environments/virtualbox/list");
    }

    private void create(VirtualBoxCreateRequestModel createModel) {
        VirtualBoxEnvironment environment = _mapper.map(createModel, VirtualBoxEnvironment.class);
        _repository.save(environment);
    }

    private void update(VirtualBoxCreateRequestModel createModel) {
        VirtualBoxEnvironment environment = _repository.get(createModel.getId(), VirtualBoxEnvironment.class);
        environment.setName(createModel.getName());
        environment.setSnapshot(createModel.getSnapshot());
        environment.setPriority(createModel.getPriority());
        _repository.update(environment);
    }

    @GetMapping({"/list", "/list/{id}"})
    public String list(Model model, @RequestParam(required = false) String filter, @PathVariable(required = false) Integer id) {
        model.addAttribute("id", ObjectUtils.firstNonNull(id, -1));
        model.addAttribute("filter", ObjectUtils.firstNonNull(filter, ""));
        model.addAttribute("name", "");
        model.addAttribute("priority", "");
        model.addAttribute("snapshot", "");
        if (id != null) {
            VirtualBoxEnvironment environment = _repository.get(id, VirtualBoxEnvironment.class);
            model.addAttribute("name", environment.getName());
            model.addAttribute("priority", environment.getPriority());
            model.addAttribute("snapshot", environment.getSnapshot());
        }
        List<VirtualBoxEnvironment> environmentList = _repository.query("from VirtualBoxEnvironment", VirtualBoxEnvironment.class);
        model.addAttribute("environmentList", environmentList);
        return "environments/virtualbox/list";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable(required = false) Integer id) {
        var environment = _repository.get(id, VirtualBoxEnvironment.class);
        _repository.remove(environment);
        return new ModelAndView("redirect:/environments/virtualbox/list");
    }
}
