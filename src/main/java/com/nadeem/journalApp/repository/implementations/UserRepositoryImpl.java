package com.nadeem.journalApp.repository.implementations;

import com.nadeem.journalApp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * This class was created to familiarise myself with MongoTemplate, Criteria and Query in SB-MongoDB
 * Crteria and Query go hand-in-hand and are used when queries become complex and derived query methods(query method DSL) cant be used.
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl {

    private final MongoTemplate mongoTemplate;

    //The purpose of this fuction is to find all the users that
    // have an email and are opted for sentiment analysis feature
    public List<User> getUserForSA() {
        Query query = new Query();
//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<User> users = mongoTemplate.find(query, User.class); // here the collectionName isnt provided but yet ORM understands what
        // must be used since it is annotated with @Docuemnt(collection ...,)
        return users;
    }
}
