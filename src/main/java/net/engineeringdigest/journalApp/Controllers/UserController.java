package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journal")
public class UserController {

    @Autowired
    private JournalEntryService journalEntryService;

    // GET all journal entries
    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll() {
        return journalEntryService.getAllEntries();
    }

    // POST a new journal entry
    @PostMapping
    public ResponseEntity<String> createEntry(@RequestBody JournalEntry myEntry) {
        return journalEntryService.saveEntry(myEntry);
    }

    // GET a journal entry by ID
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String myId) {
        return journalEntryService.getEntryById(myId);
    }

    // DELETE a journal entry by ID
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<String> deleteJournalEntryById(@PathVariable String myId) {
        return journalEntryService.deleteEntryById(myId);
    }

    // PUT (update) a journal entry by ID
    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable String myId, @RequestBody JournalEntry myEntry) {
        return journalEntryService.updateEntry(myId, myEntry);
    }
}
