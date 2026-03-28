package com.nadeem.journalApp.scheduler;

import com.nadeem.journalApp.entity.JournalEntry;
import com.nadeem.journalApp.entity.User;
import com.nadeem.journalApp.enums.Sentiment;
import com.nadeem.journalApp.repository.implementations.UserRepositoryImpl;
import com.nadeem.journalApp.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//a service becomes scheduled by annotating as @Scheduled and in paranthesis, writing a cron script/ cron exoression
//cron expressions cane be made with the help of online tools such as cronmaker etc...,

@RequiredArgsConstructor
public class EmailScheduler {

    private final EmailService emailService;
    private final UserRepositoryImpl userRepositoryImpl;

    @Scheduled(cron = "0 0 9 * * Sun")
    public void fetchUserAndSendSaMail(){
        List<User> userForSA = userRepositoryImpl.getUserForSA();
        for(User user : userForSA){
            List<JournalEntry> userJournals = user.getJournals();
            List<Sentiment> filteredSentiments = userJournals.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(JournalEntry::getSentiment)
                    .collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCount = new HashMap();
            int count = 0;
            Sentiment maxSentiment = null;
            for(Sentiment s: filteredSentiments){
                sentimentCount.put(s,sentimentCount.getOrDefault(s,0) + 1);
                if(sentimentCount.get(s) >= count){
                    maxSentiment = s;
                    count = sentimentCount.get(s);
                }
            }

            if(maxSentiment != null)
                emailService.sendEmail(user.getEmail(),"Your mood for this week ",
                        "The mood for this week by your journals is" + maxSentiment.toString());
        }

    }

}
