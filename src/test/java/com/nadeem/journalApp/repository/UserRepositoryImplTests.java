package com.nadeem.journalApp.repository;

import com.nadeem.journalApp.repository.implementations.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    public void testForSentimentAnalysis(){
        userRepositoryImpl.getUserForSA();
    }

}
