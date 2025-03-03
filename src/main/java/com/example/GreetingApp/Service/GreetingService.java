package com.example.GreetingApp.Service;
import com.example.GreetingApp.Model.Greeting;
import com.example.GreetingApp.Repository.GreetingRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GreetingService {
    private final GreetingRepository repository;

    public GreetingService(GreetingRepository repository) {
        this.repository = repository;
    }

    public Greeting saveGreeting(Greeting greeting) {
        return repository.save(greeting);
    }
    public void deleteGreeting(Long id) {
        repository.deleteById(id);
    }


    public List<Greeting> getAllGreetings() {
        return repository.findAll();
    }

    public Greeting updateGreeting(Long id, String newMessage) {
        Optional<Greeting> existingGreeting = repository.findById(id);
        if (existingGreeting.isPresent()) {
            Greeting greeting = existingGreeting.get();
            greeting.setMessage(newMessage);
            return repository.save(greeting);
        }
        return null;
    }
}
