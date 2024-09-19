package net.engineeringdigest.journalApp.Repo;

import lombok.NonNull;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserEntryRepo extends MongoRepository<User, ObjectId> {
    Optional<User> findById(@NonNull String id);
    User findUserByUserName(String userName);
}
