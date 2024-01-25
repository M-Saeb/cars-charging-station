package utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;

public class Utils {
    public static FileHandler generateFileHandler(String pattern, Formatter formatter) throws SecurityException, IOException {
        try {
            return new FileHandler(pattern, true);
        } catch (IOException e) {
            // File or directories might not exist, try creating parent directories and retry
            File file = new File(pattern);
            File parentDir = file.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    throw new IOException("Failed to create parent directories for file: " + pattern);
                }

                // Retry creating the FileHandler
                return new FileHandler(pattern, true);
            }

            // Re-throw the original IOException if creating parent directories is not possible
            throw e;
        }
    }

    public static String getTodaysDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        return dtf.format(localDate);
    }

    public static Formatter getGlobalFormatter() {
        return Logger.getLogger("").getHandlers()[0].getFormatter();
    }
}
