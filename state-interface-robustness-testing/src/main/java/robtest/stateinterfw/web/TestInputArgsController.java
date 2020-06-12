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

import java.util.Optional;

@Controller
@RequestMapping("/testsuite/{id}/input/{inputId}/args")
public class TestInputArgsController {
    private IRepository repository;

    public TestInputArgsController(IRepository repository) {
        this.repository = repository;
    }

    @GetMapping({"/add", "/add/{argId}"})
    public String add(@PathVariable Integer id, @PathVariable Integer inputId, @PathVariable Optional<Integer> argId, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("inputId", inputId);
        model.addAttribute("argId", -1);
        model.addAttribute("name", "");
        model.addAttribute("dataType", "");
        model.addAttribute("dataValue", "");
        if (argId.isPresent()) {
            model.addAttribute("argId", argId.get());
            TestInputArgument argument = repository.get(argId.get(), TestInputArgument.class);
            model.addAttribute("name", argument.getName());
            model.addAttribute("dataType", argument.getDataType());
            model.addAttribute("dataValue", argument.getDataValue());
        }
        var args = repository.query("from TestInputArgument where test_input_id = :id order by id",
                TestInputArgument.class, Param.list("id", inputId).all());
        model.addAttribute("args", args);
        return "test-suite/input/args/add";
    }

    @PostMapping({"/add", "/add/{argId}"})
    public ModelAndView add(@PathVariable Integer id, @PathVariable Integer inputId, @ModelAttribute TestInputArgsCreateModel createModel, @PathVariable Optional<Integer> argId) {
        switch (createModel.getOperation()) {
            case "post": create(inputId, createModel); break;
            case "put": put(createModel); break;
            case "delete": delete(createModel.getArgId()); break;
        }
        ModelAndView modelAndView = new ModelAndView(String.format("redirect:/testsuite/%d/input/%d/args/add", id, inputId));
        return modelAndView;
    }

    private void create(Integer inputId, TestInputArgsCreateModel createModel) {
        TestInput testInput = repository.get(inputId, TestInput.class);
        TestInputArgument argument = new TestInputArgument();
        argument.setTestInput(testInput);
        argument.setName(createModel.getName());
        argument.setDataType(createModel.getDataType());
        argument.setDataValue(createModel.getDataValue());
        repository.save(argument);
    }

    @GetMapping("/delete/{argId}")
    public ModelAndView delete(@PathVariable Integer id, @PathVariable Integer inputId, @PathVariable int argId) {
        delete((Integer) argId);
        ModelAndView modelAndView = new ModelAndView(String.format("redirect:/testsuite/%d/input/%d/args/add", id, inputId));
        return modelAndView;
    }

    private void delete(Integer argId)
    {
        TestInputArgument arg = repository.get(argId, TestInputArgument.class);
        repository.remove(arg);
    }

    public void put(TestInputArgsCreateModel createModel)
    {
        TestInputArgument argument = repository.get(createModel.getArgId(), TestInputArgument.class);
        argument.setName(createModel.getName());
        argument.setDataType(createModel.getDataType());
        argument.setDataValue(createModel.getDataValue());
        repository.update(argument);
    }
}
