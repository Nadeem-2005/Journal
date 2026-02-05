package com.nadeem.journalApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_entries")// what this says to Spring is this class's instance is equivalent of document in mongoDb i.e.., a row and maps it to a collection called journal_entries
@Data //equivalent of @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@NoArgsConstructor // required during deserialization i.e.. while using @RequestBody JournalEntry journal
public class JournalEntry {

    @Id
    private ObjectId id;
    private String title;
    private  String content;
    private LocalDateTime date;

//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }
//
//
//    public void setId(ObjectId id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
    /**
     * the reason getters and setters are commented out is because these can be inserted manually at compile
     * time by lombok and all i have to do is to mention @Getter @Setter annotation above the entity class
     */
}
