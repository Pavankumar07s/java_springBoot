package net.engineeringdigest.journalApp.Repo;

import net.engineeringdigest.journalApp.entity.FootballCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FootballCategoryRepository extends MongoRepository<FootballCategory, String> {
}
