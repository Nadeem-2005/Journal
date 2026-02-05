package com.nadeem.journalApp.controller;

import com.nadeem.journalApp.entity.JournalEntry;
import com.nadeem.journalApp.entity.User;
import com.nadeem.journalApp.services.JournalEntryService;
import com.nadeem.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal") // what this does is applies path to this class. Thud any mapping in this class will have /jounral/<path..,>
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAll(@PathVariable String userName){
        try{
            User user = userService.getUserByUsername(userName);

            List<JournalEntry> all = user.getJournals();

            if(!all.isEmpty())
                return new ResponseEntity<>(all, HttpStatus.OK);

            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        catch (Exception e){
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }


    }

//    @GetMapping("/{userName}/{myId}")
//    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId, @PathVariable String userName){ // path variable name must match
//        Optional<JournalEntry> journal= journalEntryService.showById(myId);
//        if(journal.isPresent()){
//            return new ResponseEntity<>(journal.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//    }

    @PostMapping("{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        //@RequestBody is like saying to spring that please take the request and turn it into the object of the specified class
        //make sure that input from req is of the same format as the class specified with reqbody
        try{
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,userName);

        return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userName}/{myId}")
    public ResponseEntity<Object> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName){

        journalEntryService.removeById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{userName}/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable String userName,@PathVariable ObjectId myId, @RequestBody JournalEntry updateEntry ){
        Optional<JournalEntry> old = journalEntryService.showById(myId);
        if(old.isPresent()){
            old.get().setTitle(updateEntry.getTitle() != null && !updateEntry.getTitle().equals("") ? updateEntry.getTitle() : old.get().getTitle());
            old.get().setContent(updateEntry.getContent() != null && !updateEntry.getTitle().equals("") ? updateEntry.getContent() : old.get().getContent());
            journalEntryService.saveEntry(old.get(),userName);
            return new ResponseEntity<>(old.get(),HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
