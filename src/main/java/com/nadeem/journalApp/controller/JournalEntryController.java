package com.nadeem.journalApp.controller;

import com.nadeem.journalApp.entity.JournalEntry;
import com.nadeem.journalApp.entity.User;
import com.nadeem.journalApp.services.JournalEntryService;
import com.nadeem.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping
    public ResponseEntity<?> getAll(){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
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

    @GetMapping("/{myId}")
    public ResponseEntity<?> getById(@PathVariable ObjectId myId){ // path variable name must match
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        User user = userService.getUserByUsername(userName);

        for(JournalEntry j: user.getJournals()){
            if(j.getId().equals(myId)) {
                return new ResponseEntity<>(journalEntryService.showById(myId), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("You dont have that specific journal", HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){
        //@RequestBody is like saying to spring that please take the request and turn it into the object of the specified class
        //make sure that input from req is of the same format as the class specified with reqbody
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,userName);

        return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{myId}")
    public ResponseEntity<Object> deleteJournalEntryById( @PathVariable ObjectId myId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        journalEntryService.removeById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry updateEntry ){
        Optional<JournalEntry> old = journalEntryService.showById(myId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        if(old.isPresent()){
            old.get().setTitle(updateEntry.getTitle() != null && !updateEntry.getTitle().equals("") ? updateEntry.getTitle() : old.get().getTitle());
            old.get().setContent(updateEntry.getContent() != null && !updateEntry.getTitle().equals("") ? updateEntry.getContent() : old.get().getContent());
            journalEntryService.saveEntry(old.get(),userName);
            return new ResponseEntity<>(old.get(),HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
