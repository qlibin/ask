package ask.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by alibin on 9/24/15.
 *
 * @author alibin
 */
@Document(collection = "bad_words")
public class BadWord extends AbstractEntity {

    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

}