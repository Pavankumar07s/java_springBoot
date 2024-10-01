package net.engineeringdigest.journalApp.Repo;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class userRepoIMP {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("roles").in("USER","ADMIN"));
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%±]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$").is(true));
        query.addCriteria(Criteria.where("setimentAnalesis").is(true));
        List<User> users = mongoTemplate.find(query, User.class);
        return  users;



    }
}
