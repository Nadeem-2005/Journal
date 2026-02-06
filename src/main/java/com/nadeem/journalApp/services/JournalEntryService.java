package com.nadeem.journalApp.services;

import com.nadeem.journalApp.entity.JournalEntry;
import com.nadeem.journalApp.entity.User;
import com.nadeem.journalApp.repository.JournalEntryRepository;
import com.nadeem.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    //service packages as per developer principles is used to handle business logic
    //while controller exposes endpoints and calls services to handle inside logic

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    /**
     * this tells spring that this whole functioon is to be treated as a single transaction enforcing atomicity
     * i.e.., if an error occurs , lets say after journalId is added to user (local object) but just before it can
     * be updated by userRespository's function, then instead of that refernce not being present ins user collection but the journal being present in its own,
     * all the successfull transaction rolls back
     * NOTE: for this to take place, JournalApplication must have an annotation @EnableTransactionManagement
     */
    public void saveEntry(JournalEntry journalEntry, String userName){

        try{

            User user = userRepository.findByUserName(userName);
            JournalEntry savedJournal = journalEntryRepository.save(journalEntry);
            user.getJournals().add(savedJournal);
            userRepository.save(user);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error has occured while saving the entry");
        }


        //there cant be 2 documents with same id so mongodb replaces the value instead of creating a new document
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
