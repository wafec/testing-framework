package robtest.stateinterfw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/testsuite")
public class TestSuiteController {
    @GetMapping("/index")
    public String index() {
        return "test-suite/index";
    }

    @GetMapping("/importfile")
    public String importFile() {
        return "test-suite/import-file";
    }

    @GetMapping("/list")
    public String list() {
        return "test-suite/list";
    }
}
