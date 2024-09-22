package net.engineeringdigest.journalApp.Repo;

import lombok.NonNull;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserEntryRepo extends MongoRepository<User, ObjectId> {

    // Static method to delete user by ID (though usually repository interfaces don't define static methods for this)
    static void deleteUserByID(String name) {
        // Implementation would require access to repository bean in service, static methods are not common for this.
        throw new UnsupportedOperationException("Static delete method is not recommended.");
    }

    // Find user by ID, ensuring id is not null
    Optional<User> findById(@NonNull String id);

    // Find user by username
    User findUserByUserName(String userName);

    // Delete user by ID
    void deleteById(String id);
}
