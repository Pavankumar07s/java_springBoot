package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserEntryService;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/journal")
public class JournalEntryPoint {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryService userEntryService;

    // GET all journal entries
    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        ResponseEntity<User> userResponse = userEntryService.findUserByUserName(userName);

        // Check if user was found
        if (userResponse.getBody() == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = userResponse.getBody();
        List<JournalEntry> journalEntries = user.getJournalEntries();

        // Check if user has journal entries
        if (journalEntries != null && !journalEntries.isEmpty()) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }

        return new ResponseEntity<>("No journal entries found for this user", HttpStatus.NOT_FOUND);
    }


    // POST a new journal entry
    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName) {
        try {
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    // GET a journal entry by ID
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String myId) {
        return journalEntryService.getEntryById(myId);
    }



    // DELETE a journal entry by ID
    @DeleteMapping("/id/userName/{myId}")
    public ResponseEntity<String> deleteJournalEntryById(@PathVariable String myId,@PathVariable String userName) {
        return journalEntryService.deleteEntryById(myId,userName);
    }

    // PUT (update) a journal entry by ID
    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable String myId, @RequestBody JournalEntry myEntry) {
        return journalEntryService.updateEntry(myId, myEntry);
    }
}
