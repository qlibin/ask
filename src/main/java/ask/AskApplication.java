package ask;

import com.google.common.cache.CacheBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableWebMvc
@EnableMongoRepositories(
        createIndexesForQueryMethods = true
)
@EnableMongoAuditing(setDates = true)
@EnableCaching(proxyTargetClass = false)
@EnableSpringDataWebSupport
public class AskApplication {

    public static void main(String[] args) {
        SpringApplication.run(AskApplication.class, args);
    }

    @Bean
    public GuavaCacheManager cacheManager() {
        GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
        guavaCacheManager.setCacheBuilder(
                CacheBuilder.newBuilder()
                        .expireAfterAccess(5, TimeUnit.MINUTES)
                        .maximumSize(10000)
                        .recordStats()
        );
        return guavaCacheManager;
    }

}
