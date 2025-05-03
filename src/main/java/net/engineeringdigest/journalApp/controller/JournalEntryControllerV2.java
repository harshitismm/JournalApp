package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService entryService;

    @Autowired
    private UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String username){
        User user = userService.findByUsername(username);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if(allEntries!=null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId id){
        Optional<JournalEntry> entry = entryService.getEntryById(id);
        if(entry.isPresent()){
            return new ResponseEntity<>(entry.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String username){

        try{
            entryService.saveJournalEntry(myEntry,username);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch(Exception exception){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
    }

    @PutMapping("id/{username}/{myid}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myid,
                                         @RequestBody JournalEntry newjournalEntry,
                                         @PathVariable String username){
        entryService.updateById(myid,newjournalEntry,username);
        return new ResponseEntity<>(entryService.getEntryById(myid),HttpStatus.OK);
    }

    @DeleteMapping("id/{username}/{myid}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myid, @PathVariable String username){
        entryService.deleteEntry(myid,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
