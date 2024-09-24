package net.engineeringdigest.journalApp.Controllers;


import net.engineeringdigest.journalApp.Service.UserEntryService;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class adminController {
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUser() {
        List<User> all = userEntryService.getAllUsers().getBody();
        if (all!=null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping("/create-AdminUser")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        try {
            userEntryService.saveAdmin(user);
            return new ResponseEntity<>("created ADmin",HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Kuch to Fatta",HttpStatus.NOT_FOUND);
        }


    }

}
