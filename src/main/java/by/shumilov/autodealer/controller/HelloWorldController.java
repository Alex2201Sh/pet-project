package by.shumilov.autodealer.controller;

//import by.shumilov.autodealer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloWorldController {

//    private final UserRepository userRepository;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello3456789");
    }

//    @GetMapping("/users")
//    public ResponseEntity<?> getUsers() {
//        return ResponseEntity.ok(userRepository.findAll());
//    }
}