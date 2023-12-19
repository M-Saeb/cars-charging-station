import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Formatter;

public class Utils {
    public static FileHandler generateFileHandler(String pattern, Formatter formatter, Filter filter) throws SecurityException, IOException {
        FileHandler handler = new FileHandler(pattern, true);
        handler.setFormatter(formatter);
        handler.setFilter(filter);
        return handler;
    }
}
