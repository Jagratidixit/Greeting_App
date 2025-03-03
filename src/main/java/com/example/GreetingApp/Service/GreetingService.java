package com.example.GreetingApp.Service;

import com.example.GreetingApp.Model.Greeting;
import com.example.GreetingApp.Repository.GreetingRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GreetingService {
    private final GreetingRepository repository;

    public GreetingService(GreetingRepository repository) {
        this.repository = repository;
    }
    public Greeting saveGreeting(Greeting greeting) {
        return repository.save(greeting);
    }


    public List<Greeting> getAllGreetings() {
        return repository.findAll();
    }
}
