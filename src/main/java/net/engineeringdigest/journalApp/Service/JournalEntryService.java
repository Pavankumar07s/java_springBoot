package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Repo.JournalEntryRepo;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    // Save a new journal entry
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepo.save(journalEntry);
    }

    // Get all journal entries
    public List<JournalEntry> getAllEntries() {
        return journalEntryRepo.findAll();
    }

    // Get a journal entry by ID
    public JournalEntry getEntryById(String id) {
        Optional<JournalEntry> entry = journalEntryRepo.findById(id);
        return entry.orElse(null); // Return the entry if found, else return null
    }

    // Delete a journal entry by ID
    public boolean deleteEntryById(String id) {
        Optional<JournalEntry> entry = journalEntryRepo.findById(id);
        if (entry.isPresent()) {
            journalEntryRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // Update a journal entry by ID
    public JournalEntry updateEntry(String id, JournalEntry updatedEntry) {
        Optional<JournalEntry> existingEntry = journalEntryRepo.findById(id);
        if (existingEntry.isPresent()) {
            JournalEntry journalEntry = existingEntry.get();
            journalEntry.setTitle(updatedEntry.getTitle());
            journalEntry.setContent(updatedEntry.getContent());
            journalEntry.setDate(updatedEntry.getDate());
            return journalEntryRepo.save(journalEntry); // Save the updated entry
        }
        return null;
    }
}
