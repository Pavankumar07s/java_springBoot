package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Repo.UserEntryRepo;
import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserEntryService;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryRepo userEntryRepo;
    // GET all journal entries
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUser() {
//        return userEntryService.getAllUsers();
//    }


//    @PutMapping("/{userName}")
//    public ResponseEntity<?> updateUser(@RequestBody User user ,@PathVariable String userName) {
//        ResponseEntity<User> userResponse = userEntryService.findUserByUserName(userName);
//        User userInDB = userResponse.getBody();
//
//        // Check if user exists
//        if (userInDB != null) {
//            // Update user details
//            userInDB.setUserName(user.getUserName());
//            userInDB.setPassword(user.getPassword());
//
//            // Save the updated user
//            userEntryService.createUser(userInDB);
//
//            // Return NO_CONTENT status if successful
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        // Return NOT_FOUND if user does not exist
//        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
//    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        // Check if user exists
        ResponseEntity<User> userResponse = userEntryService.findUserByUserName(userName);
        User userInDB = userResponse.getBody();
            // Update user details
        try {
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());

            // Save the updated user
            userEntryService.createNewUser(userInDB);
            return new ResponseEntity<>("User Updated:", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Return NOT_FOUND if user does not exist
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }



    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteByID() {
        try {
            // Get the authenticated user's name
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Fetch the user by username
            User user = userEntryRepo.findUserByUserName(username);

            // If user is found, delete by id
            if (user != null) {
                userEntryRepo.deleteById(user.getId().toString());
                return new ResponseEntity<>("User removed", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
