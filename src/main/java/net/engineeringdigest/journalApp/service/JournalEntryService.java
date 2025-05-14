package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveJournalEntry(JournalEntry entry, String username){
        User user = userService.findByUsername(username);

        JournalEntry saved = journalEntryRepository.save(entry);
        user.getJournalEntries().add(saved);
        userService.createUser(user);
    }

    public List<JournalEntry> getAllEntries(){
        List<JournalEntry> entriesList;
        entriesList = journalEntryRepository.findAll();
        return entriesList;
    }

    public Optional<JournalEntry> getEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public String updateById(ObjectId id, JournalEntry newEntry, String username){
        JournalEntry oldentry = journalEntryRepository.findById(id).orElse(null);
        if(newEntry!=null && oldentry!=null){
            oldentry.setContent(newEntry.getContent());
            oldentry.setTitle(newEntry.getTitle());
        }

        journalEntryRepository.save(oldentry);
        return "updated";
    }

    @Transactional
    public Boolean deleteEntry(ObjectId id, String username){
        boolean removed=false;
        try {
            User user = userService.findByUsername(username);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.createUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error in deleting the entry:"+e);
        }
        return removed;


    }

}
