package com.example.GreetingApp.Controller;
import com.example.GreetingApp.Model.Greeting;
import com.example.GreetingApp.Service.GreetingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/greetings")
public class GreetingController {
    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @PostMapping
    public Greeting saveGreeting(@RequestParam String message) {
        Greeting greeting = new Greeting(message);
        return greetingService.saveGreeting(greeting);
    }
    @DeleteMapping("/{id}")
    public String deleteGreeting(@PathVariable Long id) {
        greetingService.deleteGreeting(id);
        return "Greeting with ID " + id + " deleted successfully.";
    }


    @GetMapping
    public List<Greeting> getAllGreetings() {
        return greetingService.getAllGreetings();
    }

    @PutMapping("/{id}")
    public Greeting updateGreeting(@PathVariable Long id, @RequestParam String message) {
        return greetingService.updateGreeting(id, message);
    }
}
