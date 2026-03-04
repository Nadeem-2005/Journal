package com.nadeem.journalApp.config;

import com.nadeem.journalApp.entity.ConfigurationEntity;
import com.nadeem.journalApp.repository.ConfigRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 * The purpose of this class file is to retreve things such as api-keys , URL's etc..,
 * which dont change frequently
 * We can say that making a call to DB everytime i need anything from it instead of hardcoding increases latency.
 * But the above issue can be avoided using @PostConstruct which is used over a function.
 * What this does is that ,when a bean is created of this AppCache, postconstruct basically forces spring to run
 * the function annotated as @PostConstruct at its creation. This inturn makes one call to DB and stores it in-memory
 * inside the map thus behaving as a in-mem cache.
 */
@Component
@RequiredArgsConstructor
public class AppCache {

    private final ConfigRepository configRepository;

    public Map<String,String > appCache;

    @PostConstruct
    public void init(){
        List<ConfigurationEntity> all = configRepository.findAll();
        appCache = all.stream().
                collect(Collectors.toMap(ConfigurationEntity::getKey, ConfigurationEntity::getValue));
    }


}
