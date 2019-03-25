package testtool;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonFileReader {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final String path;

    public JsonFileReader(final String path) {
        this.path = path;
    }

    public String content() throws IOException, URISyntaxException {
        Path resource = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(path)).toURI());
        Stream<String> lines = Files.lines(resource);
        String content = lines
                .filter(line -> !line.isEmpty() && !line.startsWith("//"))
                .map(String::trim)
                .collect(Collectors.joining(LINE_SEPARATOR));
        lines.close();

        return content;
    }
}
