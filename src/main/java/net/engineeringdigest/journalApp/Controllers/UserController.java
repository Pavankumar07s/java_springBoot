package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Repo.UserEntryRepo;
import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserEntryService;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private JournalEntryService journalEntryService;
    // GET all journal entries
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return userEntryService.getAllUsers();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userEntryService.createUser(user);
    }
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user ,@PathVariable String userName) {
        ResponseEntity<User> userResponse = userEntryService.findUserByUserName(userName);
        User userInDB = userResponse.getBody();

        // Check if user exists
        if (userInDB != null) {
            // Update user details
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());

            // Save the updated user
            userEntryService.createUser(userInDB);

            // Return NO_CONTENT status if successful
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Return NOT_FOUND if user does not exist
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}
