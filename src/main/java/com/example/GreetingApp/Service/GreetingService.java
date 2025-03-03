package com.example.GreetingApp.Service;


import com.example.GreetingApp.Model.Greeting;
import com.example.GreetingApp.Repository.GreetingRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class GreetingService {
    private final GreetingRepository repository;

    public GreetingService(GreetingRepository repository) {
        this.repository = repository;
    }

    public Greeting saveGreeting(String message) {
        return repository.save(new Greeting(message));
    }

    public Greeting findGreetingById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
