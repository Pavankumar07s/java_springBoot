package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserEntryService;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/journal")
public class JournalEntryPoint {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryService userEntryService;

    // GET all journal entries
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ResponseEntity<User> userResponse = userEntryService.findUserByUserName(username);

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
    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    // GET a journal entry by ID
    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userEntryService.findUserByUserName(username).getBody();

        List<JournalEntry> collect = user.getJournalEntries().stream()
                .filter(x -> x.getId().equals(myId))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            return journalEntryService.getEntryById(myId);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




    // DELETE a journal entry by ID
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<String> deleteJournalEntryById(@PathVariable String myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return journalEntryService.deleteEntryById(myId,username);
    }

    // PUT (update) a journal entry by ID
    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable String myId, @RequestBody JournalEntry myEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return journalEntryService.updateEntry(myId, myEntry,username);
    }
}
