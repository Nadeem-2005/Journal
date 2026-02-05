package com.nadeem.journalApp.repository;

import com.nadeem.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {



}

