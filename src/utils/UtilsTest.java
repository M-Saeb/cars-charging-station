package utils;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;

import junit.framework.TestCase;


class MyFormatter extends Formatter {

	@Override
	public String format(LogRecord arg0) {
		return "nothing";
	}}

public class UtilsTest extends TestCase {
	String testFolderName = "test_folder";


	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		File testFolder = new File(testFolderName);
		if (testFolder.exists()){
			testFolder.delete();
		}
	}

	/**
	 * Test method for {@link utils.Utils#generateFileHandler(java.lang.String, java.util.logging.Formatter)}.
	 */
	public void testGenerateFileHandler() {
		String pattern = "test_folder";
		Formatter formatter = new MyFormatter();
		FileHandler fileHandler;
		try {
			fileHandler = Utils.generateFileHandler(pattern, formatter);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
			fail("couldn't initialize file handler.");
			return;
		}
		assertSame(fileHandler.getFormatter(), formatter);
	}

	/**
	 * Test method for {@link utils.Utils#getTodaysDate()}.
	 */
	public void testGetTodaysDate() {
		String generatedDate = Utils.getTodaysDate();
        LocalDate today = LocalDate.now();

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Format the date using the formatter
        String formattedDate = today.format(formatter);

		assertEquals(generatedDate, formattedDate);
	}

	/**
	 * Test method for {@link utils.Utils#getGlobalFormatter()}.
	 */
	public void testGetGlobalFormatter() {
		Formatter returnedFormatter = Utils.getGlobalFormatter();
		assertNotNull(returnedFormatter);
	}

}
