package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.entity.JournalENtry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api/journal")
public class JournalEntryPoint {


    private Map<Long,JournalENtry>JournalEntry=new HashMap<>();

    @GetMapping
    public List<JournalENtry> getAll(){
        return new ArrayList<>(JournalEntry.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalENtry myENtry){
        JournalEntry.put(myENtry.getId(),myENtry);
        return true;
    }

    @GetMapping("id/{myId}")
    public JournalENtry getJournalENtryBYId(@PathVariable Long myId){
        return JournalEntry.get(myId);
    }

    @DeleteMapping("id/{myId}")
    public JournalENtry delJournalENtryBYId(@PathVariable Long myId){
        try {
            return JournalEntry.remove(myId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("id/{myId}")
    public JournalENtry updateJournalENtryBYId(@PathVariable Long myId,@RequestBody JournalENtry myEntry){
        return JournalEntry.put(myId,myEntry);
    }
}
