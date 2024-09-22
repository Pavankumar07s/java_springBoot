package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserEntryService;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class publicUserController {
    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private JournalEntryService journalEntryService;
//     GET all journal entries
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return userEntryService.getAllUsers();
    }
    @PostMapping
    public void createUser(@RequestBody User user){
        userEntryService.createUser(user);
    }
}
