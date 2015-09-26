package ask;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alibin on 9/26/15.
 *
 * @author alibin
 */
public class Fixture {

    public static final String baseUrl = "http://localhost";

    public static final String testQuestionText = System.nanoTime() + " What would be if i post a question like this?";

    public static final String badWord = "whooy";

    public static final String testInappropriateQuestionText = testQuestionText + " you are " + badWord;

}
