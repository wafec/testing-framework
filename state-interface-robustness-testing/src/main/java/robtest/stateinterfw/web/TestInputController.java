package robtest.stateinterfw.web;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import robtest.stateinterfw.data.*;
import robtest.stateinterfw.web.models.TestInputArgsCreateModel;
import robtest.stateinterfw.web.models.TestInputCreateModel;

import java.util.ArrayList;
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
        model.addAttribute("inputId", ObjectUtils.firstNonNull(inputId, -1));
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
        switch (createModel.getOperation()) {
            case "post": create(id, createModel); break;
            case "put": update(createModel); break;
        }
        create(id, createModel);
        ModelAndView modelAndView = new ModelAndView(String.format("redirect:/testsuite/%d/input/manage", id));
        return modelAndView;
    }

    private void create(Integer id, TestInputCreateModel createModel) {
        TestCase testCase = repository.get(id, TestCase.class);
        TestInput testInput = new TestInput();
        testInput.setTestCase(testCase);
        testInput.setAction(createModel.getAction());
        testInput.setLocked(createModel.getLocked());
        testInput.setOrder(createModel.getOrder());
        repository.save(testInput);
    }

    private void update(TestInputCreateModel createModel) {
        TestInput testInput = repository.get(createModel.getId(), TestInput.class);
        testInput.setAction(createModel.getAction());
        testInput.setLocked(createModel.getLocked());
        testInput.setOrder(createModel.getOrder());
        repository.update(testInput);
    }

    @GetMapping("/delete/{inputId}")
    public ModelAndView delete(@PathVariable Integer id, @PathVariable Integer inputId) {
        delete(inputId);
        ModelAndView modelAndView = new ModelAndView(String.format("redirect:/testsuite/%d/input/manage", id));
        return modelAndView;
    }

    private void delete(Integer id) {
        try (var transactionRepository = ((ITransactionRepositoryFactory) repository).getTransaction()) {
            TestInput testInput = transactionRepository.get(id, TestInput.class);
            for (var arg : ObjectUtils.firstNonNull(testInput.getTestInputArguments(), new ArrayList<TestInputArgument>())) {
                transactionRepository.remove(arg);
            }
            for (var state : ObjectUtils.firstNonNull(testInput.getTestStates(), new ArrayList<TestState>())) {
                transactionRepository.remove(state);
            }
            for (var message : ObjectUtils.firstNonNull(testInput.getTestMessages(), new ArrayList<TestMessage>())) {
                transactionRepository.remove(message);
            }
            transactionRepository.remove(testInput);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
