package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService service;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(){
        List<JournalEntry> allEntries = service.getAllEntries();
        if(allEntries!=null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId id){
        Optional<JournalEntry> entry = service.getEntryById(id);
        if(entry.isPresent()){
            return new ResponseEntity<>(entry.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        service.saveJournalEntry(myEntry);
        try{
            service.saveJournalEntry(myEntry);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch(Exception exception){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
    }

    @PutMapping("id/{myid}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myid,@RequestBody JournalEntry newjournalEntry){
        service.updateById(myid,newjournalEntry);
        return new ResponseEntity<>(service.getEntryById(myid),HttpStatus.OK);
    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myid){
        service.deleteEntry(myid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
