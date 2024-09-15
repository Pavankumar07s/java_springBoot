package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journal")
public class JournalEntryPoint {

    @Autowired
    private JournalEntryService journalEntryService;

    // GET all journal entries
    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAllEntries();
    }

    // POST a new journal entry
    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry) {
        journalEntryService.saveEntry(myEntry);
        return true;
    }

    // GET a journal entry by ID
    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable String myId) { // String is used for MongoDB IDs
        return journalEntryService.getEntryById(myId);
    }

    // DELETE a journal entry by ID
    @DeleteMapping("/id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable String myId) {
        return journalEntryService.deleteEntryById(myId);
    }

    // PUT (update) a journal entry by ID
    @PutMapping("/id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable String myId, @RequestBody JournalEntry myEntry) {
        return journalEntryService.updateEntry(myId, myEntry);
    }
}
