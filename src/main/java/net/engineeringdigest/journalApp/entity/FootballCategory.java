package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "football_categories")
@Data
@NoArgsConstructor
public class FootballCategory
{
    @Id
    private String id;  // MongoDB automatically generates _id
    private String categoryName;
    private String description;

    // Constructors, Getters, Setters (You can use Lombok to generate these)

    public FootballCategory(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }


}
