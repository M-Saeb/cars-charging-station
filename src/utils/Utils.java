package utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;

public class Utils {
    public static FileHandler generateFileHandler(String pattern, Formatter formatter) throws SecurityException, IOException {
        FileHandler handler = new FileHandler(pattern, true);
        handler.setFormatter(formatter);
        return handler;
    }

    public static String getTodaysDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		return dtf.format(localDate);
    }

    public static Formatter getGlobalFormatter(){
        return Logger.getLogger("").getHandlers()[0].getFormatter();
    } 
}
