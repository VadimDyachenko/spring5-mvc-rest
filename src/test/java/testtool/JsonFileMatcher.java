package testtool;

import org.springframework.test.util.JsonExpectationsHelper;
import org.springframework.test.web.servlet.ResultMatcher;

public final class JsonFileMatcher {

    private JsonFileMatcher() {
        super();
    }

    public static ResultMatcher jsonFromFile(String file) {
        return result -> {
            final String expected = new JsonFileReader(file).content();
            final String actual = result.getResponse().getContentAsString();
            new JsonExpectationsHelper().assertJsonEqual(expected, actual, true);
        };
    }
}
