package robtest.stateinterfw.web;

import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String create(@ModelAttribute VirtualBoxCreateRequestModel createModel, Model model) {
        VirtualBoxEnvironment environment = _mapper.map(createModel, VirtualBoxEnvironment.class);
        _repository.save(environment);
        model.addAttribute("environment", environment);
        return "/environments/virtualbox/result";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<VirtualBoxEnvironment> environmentList = _repository.query("from VirtualBoxEnvironment", VirtualBoxEnvironment.class);
        model.addAttribute("environmentList", environmentList);
        return "environments/virtualbox/list";
    }
}
