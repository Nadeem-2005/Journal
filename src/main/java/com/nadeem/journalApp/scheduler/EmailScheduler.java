package com.nadeem.journalApp.scheduler;

import com.nadeem.journalApp.entity.JournalEntry;
import com.nadeem.journalApp.entity.User;
import com.nadeem.journalApp.repository.implementations.UserRepositoryImpl;
import com.nadeem.journalApp.services.EmailService;
import com.nadeem.journalApp.services.SentimentAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

//a service becomes scheduled by annotating as @Scheduled and in paranthesis, writing a cron script/ cron exoression
//cron expressions cane be made with the help of online tools such as cronmaker etc...,

@RequiredArgsConstructor
public class EmailScheduler {

    private final EmailService emailService;
    private final UserRepositoryImpl userRepositoryImpl;
    private final SentimentAnalysisService sentimentAnalysisService;


    @Scheduled(cron = "0 0 9 * * Sun")
    public void fetchUserAndSendSaMail(){
        List<User> userForSA = userRepositoryImpl.getUserForSA();
        for(User user : userForSA){
            List<JournalEntry> userJournals = user.getJournals();
            List<String> filteredEntries = userJournals.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(JournalEntry::getContent)
                    .collect(Collectors.toList());
            String allEntries = String.join(" ", filteredEntries);
            int sAResult = sentimentAnalysisService.sentimentAnalysis(allEntries);
            switch (sAResult){
                case 1:
                    emailService.sendEmail(user.getEmail(),
                            "Your weekly sentiment report",
                            "Your feelings for this week is HAPPY");
                    break;
                case 2:

            }
        }

    }

}
