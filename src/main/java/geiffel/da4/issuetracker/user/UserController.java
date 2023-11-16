package geiffel.da4.issuetracker.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(@Qualifier("jpa") UserService userService) {
        this.userService=userService;
    }


    @GetMapping("")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }
/*
    @PostMapping("")
    public ResponseEntity createUser(@RequestBody User user) {
        String regexNom = "^[a-zA-Z]+$";
        String regexId = "^[0-9]+$";
        if(Pattern.matches(regexNom,user.getNom())) {
            if (Pattern.matches(regexId, user.getId().toString())) {
                User created_user = userService.create(user);
                return ResponseEntity.created(URI.create("/users/" + created_user.getId().toString())).build();
            }
        }

        return null;

    }

 */

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
                User createdUser = userService.create(user);
                return ResponseEntity.created(URI.create("/users/" + createdUser.getId().toString())).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.update(id, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
