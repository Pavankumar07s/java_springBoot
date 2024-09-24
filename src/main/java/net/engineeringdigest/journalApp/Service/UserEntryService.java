package net.engineeringdigest.journalApp.Service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Repo.UserEntryRepo;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserEntryService {

    @Autowired
    private UserEntryRepo userEntryRepo;

//    private static final Logger logger= LoggerFactory.getLogger(UserEntryService.class);
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    // Save a new User entry
    public void createNewUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userEntryRepo.save(user);
            new ResponseEntity<>("user created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("heheheh for {}",user.getUserName());
//            logger.info("hehehe");
//            logger.debug("hohoho");
//            logger.error("tututu");
            new ResponseEntity<>("User creation failed.", HttpStatus.NOT_ACCEPTABLE);
        }

    }

    // Get all User entries
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> entries = userEntryRepo.findAll();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    // Get a User entry by userName
    public ResponseEntity<User> findUserByUserName(@NonNull String userName) {
        Optional<User> entry = Optional.ofNullable(userEntryRepo.findUserByUserName(userName));
        return entry.map(journalEntry -> new ResponseEntity<>(journalEntry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete a User entry by ID
    public ResponseEntity<String> deleteEntryById(String id) {
        Optional<User> entry = userEntryRepo.findById(id);
        if (entry.isPresent()) {
            userEntryRepo.deleteById(id);
            return new ResponseEntity<>("Journal entry deleted successfully.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Journal entry not found.", HttpStatus.NOT_FOUND);
    }

    // Update a journal entry by ID
    public ResponseEntity<User> updateEntry(ObjectId id, User updatedEntry) {
        Optional<User> existingEntry = userEntryRepo.findById(id);
        if (existingEntry.isPresent()) {
            User user = existingEntry.get();
            user.setUserName(updatedEntry.getUserName());
            user.setPassword(updatedEntry.getPassword());
            User savedEntry = userEntryRepo.save(user);
            return new ResponseEntity<>(savedEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Save or update an entry
    public void saveUser(User user) {
        userEntryRepo.save(user);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userEntryRepo.save(user);
        new ResponseEntity<>("Journal entry created successfully.", HttpStatus.CREATED);
    }
}
