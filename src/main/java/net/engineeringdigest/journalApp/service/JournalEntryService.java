package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public String saveJournalEntry(JournalEntry entry){
        journalEntryRepository.save(entry);
        return "Entry is saved";
    }

    public List<JournalEntry> getAllEntries(){
        List<JournalEntry> entriesList;
        entriesList = journalEntryRepository.findAll();
        return entriesList;
    }

    public Optional<JournalEntry> getEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public String updateById(ObjectId id,JournalEntry newEntry){
        JournalEntry oldentry = journalEntryRepository.findById(id).orElse(null);
        if(newEntry!=null && oldentry!=null){
            oldentry.setContent(newEntry.getContent());
            oldentry.setTitle(newEntry.getTitle());
        }

        journalEntryRepository.save(oldentry);
        return "updated";
    }

    public String deleteEntry(ObjectId id){
        journalEntryRepository.deleteById(id);
        return "OK";
    }

}
