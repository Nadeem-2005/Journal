package com.nadeem.journalApp.repository;

import com.nadeem.journalApp.entity.ConfigurationEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends MongoRepository<ConfigurationEntity, ObjectId> {
}
