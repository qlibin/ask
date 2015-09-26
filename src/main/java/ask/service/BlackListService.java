package ask.service;

import ask.domain.BadWord;
import ask.domain.Question;
import ask.repository.BadWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by alibin on 9/24/15.
 *
 * @author alibin
 */
@Service
public class BlackListService {

    @Autowired
    private BadWordRepository badWordRepository;

    @Caching(evict = {
            @CacheEvict(value = "blackList")
    })
    public BadWord add(String word) {

        BadWord badWord = badWordRepository.findByWord(word);
        if (badWord == null) {
            badWord = badWordRepository.save(BadWord.fromWord(word));
        }
        return badWord;

    }

    /**
     * Cacheable black list
     * @return set of bad words
     */
    @Cacheable(value = "blackList")
    public Set<String> blackList() {
        Set<String> result = new HashSet<>();
        List<BadWord> badWords = badWordRepository.findAll();
        result.addAll(badWords.stream().map(BadWord::getWord).collect(Collectors.toList()));
        return result;
    }

    /**
     * Check if text not contains words from the black list
     * @param text question text
     * @return true - if text not contains words from the black list
     */
    public boolean check(String text) {
        Set<String> blackList = blackList();
        if (StringUtils.hasText(text)) {
            String[] words = splitWords(text);
            for (String word : words) {
                if (blackList.contains(word)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * split text using all possible separators
     * @param text text to split
     * @return array of words
     */
    @Cacheable(value = "splitWords")
    private String[] splitWords(String text) {
        return text.split("[\\s,.:!\\(\\)\\-=+\\[\\]\\{\\}*`\"'~@#\\$%^&<>|\\\\\\/?]+");
    }


}
