package com.nadeem.journalApp.services;

import com.nadeem.journalApp.entity.JournalEntry;
import com.nadeem.journalApp.entity.User;
import com.nadeem.journalApp.repository.JournalEntryRepository;
import com.nadeem.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JournalEntryService {
    //service packages as per developer principles is used to handle business logic
    //while controller exposes endpoints and calls services to handle inside logic

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(JournalEntry journalEntry, String userName){

        User user = userRepository.findByUserName(userName);
        JournalEntry savedJournal = journalEntryRepository.save(journalEntry);
        user.getJournals().add(savedJournal);
        userRepository.save(user);
        //there cant be 2 documents with same id so mongodb replaces the value insttead of creating a new document
    }

    public List<JournalEntry> showAll(){
//        System.out.println(journalEntryRepository.findAll());
        return new ArrayList<JournalEntry>(journalEntryRepository.findAll());
//        return null;
    }

    public Optional<JournalEntry> showById(ObjectId id){
//        return journalEntryRepository.findById(id).orElse(null); // what this does is either returns value if its present or else null (to be used with optional types)
            return journalEntryRepository.findById(id);
    }

    public void removeById(ObjectId id, String userName){
        User user = userRepository.findByUserName(userName);
        user.getJournals().removeIf(x -> x.getId().equals(id));
        userRepository.save(user);
        journalEntryRepository.deleteById(id);
    }

}
