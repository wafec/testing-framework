package robtest.stateinterfw.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import robtest.stateinterfw.data.*;
import robtest.stateinterfw.web.models.TestInputArgsCreateModel;
import robtest.stateinterfw.web.models.TestInputCreateModel;

import java.util.Optional;

@Controller
@RequestMapping("/testsuite/{id}/input")
public class TestInputController {
    private IRepository repository;

    public TestInputController(IRepository repository) {
        this.repository = repository;
    }

    private void loadTestInputs(Model model, int id) {
        var testInputs = repository.query("from TestInput where testCase.id = :id order by order",
                TestInput.class, Param.list("id", id).all());
        model.addAttribute("testInputs", testInputs);
    }

    @GetMapping({"/manage", "/manage/{inputId}"})
    public String index(@PathVariable int id, Model model, @PathVariable(required = false) Integer inputId) {
        model.addAttribute("id", id);
        model.addAttribute("inputId", inputId);
        model.addAttribute("inputAction", "");
        model.addAttribute("inputLocked", false);
        model.addAttribute("inputOrder", 1);
        loadTestInputs(model, id);
        if (inputId != null) {
            var testInput = repository.get(inputId, TestInput.class);
            model.addAttribute("inputAction", testInput.getAction());
            model.addAttribute("inputLocked", testInput.isLocked());
            model.addAttribute("inputOrder", testInput.getOrder());
        }
        return "test-suite/input/manage";
    }

    @PostMapping("/action/add")
    public ModelAndView addAction(@PathVariable int id, @ModelAttribute TestInputCreateModel createModel, Model model) {
        model.addAttribute("id", id);
        TestCase testCase = repository.get(id, TestCase.class);
        TestInput testInput = new TestInput();
        testInput.setTestCase(testCase);
        testInput.setAction(createModel.getAction());
        testInput.setLocked(createModel.getLocked());
        testInput.setOrder(createModel.getOrder());
        repository.save(testInput);
        ModelAndView modelAndView = new ModelAndView(String.format("redirect:/testsuite/%d/input/manage", id));
        return modelAndView;
    }
}
