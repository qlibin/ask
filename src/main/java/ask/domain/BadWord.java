package ask.domain;

import com.google.common.base.Objects;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by alibin on 9/24/15.
 *
 * @author alibin
 */
@Document(collection = "bad_words")
public class BadWord extends AbstractEntity {
    public BadWord() {
    }
    public BadWord(String word) {
        this.word = word;
    }

    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("word", word)
                .toString();
    }

    public static BadWord fromWord(String word) {
        return new BadWord(word);
    }

}