package robtest.stateinterfw.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.Param;
import robtest.stateinterfw.data.TestInput;
import robtest.stateinterfw.data.TestInputArgument;
import robtest.stateinterfw.web.models.TestInputArgsCreateModel;

@Controller
@RequestMapping("/testsuite/{id}/input/{inputId}/args")
public class TestInputArgsController {
    private IRepository repository;

    public TestInputArgsController(IRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/add")
    public String add(@PathVariable Integer id, @PathVariable Integer inputId, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("inputId", inputId);
        var args = repository.query("from TestInputArgument where test_input_id = :id order by id",
                TestInputArgument.class, Param.list("id", inputId).all());
        model.addAttribute("args", args);
        return "test-suite/input/args/add";
    }

    @PostMapping("/add")
    public ModelAndView add(@PathVariable Integer id, @PathVariable Integer inputId, @ModelAttribute TestInputArgsCreateModel createModel) {
        TestInput testInput = repository.get(inputId, TestInput.class);
        TestInputArgument argument = new TestInputArgument();
        argument.setTestInput(testInput);
        argument.setName(createModel.getName());
        argument.setDataType(createModel.getDataType());
        argument.setDataValue(createModel.getDataValue());
        repository.save(argument);
        ModelAndView modelAndView = new ModelAndView(String.format("redirect:/testsuite/%d/input/%d/args/add", id, inputId));
        return modelAndView;
    }
}
