package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Repo.JournalEntryRepo;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserEntryService userEntryService;

    // Save a new journal entry for a user
    public void saveEntry(JournalEntry journalEntry, String userName) {
        User user = userEntryService.findUserByUserName(userName).getBody();
        if (user == null) {
            new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            return;
        }

        JournalEntry savedEntry = journalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(savedEntry);
        userEntryService.saveEntry(user);

        new ResponseEntity<>("Journal entry created and assigned to user successfully.", HttpStatus.CREATED);
    }

    // Get all journal entries
    public ResponseEntity<List<JournalEntry>> getAllEntries() {
        List<JournalEntry> entries = journalEntryRepo.findAll();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    // Get a journal entry by ID
    public ResponseEntity<JournalEntry> getEntryById(String id) {
        Optional<JournalEntry> entry = journalEntryRepo.findById(id);
        return entry.map(journalEntry -> new ResponseEntity<>(journalEntry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete a journal entry by ID
    public ResponseEntity<String> deleteEntryById(String id, String userName) {
        Optional<JournalEntry> entry = journalEntryRepo.findById(id);
        User user = userEntryService.findUserByUserName(userName).getBody();

        if (entry.isPresent()) {
            user.getJournalEntries().removeIf(x->x.getId().equals(id));
            UserEntryService.saveEntry(user);
            journalEntryRepo.deleteById(id);
            return new ResponseEntity<>("Journal entry deleted successfully.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Journal entry not found.", HttpStatus.NOT_FOUND);
    }

    // Update a journal entry by ID
    public ResponseEntity<JournalEntry> updateEntry(String id, JournalEntry updatedEntry) {
        Optional<JournalEntry> existingEntry = journalEntryRepo.findById(id);
        if (existingEntry.isPresent()) {
            JournalEntry journalEntry = existingEntry.get();
            journalEntry.setTitle(updatedEntry.getTitle());
            journalEntry.setContent(updatedEntry.getContent());
            journalEntry.setDate(updatedEntry.getDate());
            JournalEntry savedEntry = journalEntryRepo.save(journalEntry);
            return new ResponseEntity<>(savedEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
