package basicarithmeticgenarator.example;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/examples")
public class ExampleController {

    private ExampleService exampleService;

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }


    @GetMapping
    public ExampleDTO getExamples() {
        return exampleService.getExamples();
    }


    @PostMapping
    public String createExample(@RequestBody CreateExampleCommand command) {
        return exampleService.createExample(command);
    }


    @DeleteMapping
    public void deleteExamples() {
        exampleService.deleteExamples();
    }
}
