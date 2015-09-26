package ask.domain;

import com.google.common.base.Objects;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by alibin on 9/24/15.
 *
 * @author alibin
 */
@Document(collection = "questions")
public class Question extends AbstractEntity {

    private String text;

    private String country;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("text", text)
                .add("country", country)
                .toString();
    }
}
