package com.example.GreetingApp.Service;


import com.example.GreetingApp.Model.Greeting;
import com.example.GreetingApp.Repository.GreetingRepository;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    private final GreetingRepository repository;

    public GreetingService(GreetingRepository repository) {
        this.repository = repository;
    }

    public Greeting saveGreeting(String message) {
        Greeting greeting = new Greeting(message);
        return repository.save(greeting);
    }
}
