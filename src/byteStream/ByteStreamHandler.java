package byteStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class ByteStreamHandler extends Handler
{
	FileOutputStream file = null;
	Formatter formatter;

    public ByteStreamHandler(String pattern) {
		// Use the formatter we have already set
		formatter = Logger.getLogger("").getHandlers()[0].getFormatter();
    	 try {
    		File tempFile = new File(pattern); 
    		if(tempFile.exists())
    		{
    			this.file = new FileOutputStream(pattern, true);
    		}
    		else 
    		{
				try {
					boolean createFile = tempFile.createNewFile();
					if(createFile)
					{
						this.file = new FileOutputStream(pattern, true);
					}
					else {
						throw new IOException("Failed to create the file...");
					}
				} catch (IOException e) {
					System.out.println("An error occurred: " + e.getMessage());
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
		}
    }

	@Override
	public Formatter getFormatter() {
		// TODO Auto-generated method stub
		return formatter;
	}

    @Override
    public void close() throws SecurityException {
    	try {
			this.file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void flush() {
		try {
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	} 
    
    @Override
    public void publish(LogRecord arg0) 
    {
		if (!isLoggable(arg0)){
			return;
		}
        byte[] logBytes = getFormatter().format(arg0).getBytes();
    	try {
	    	file.write(logBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally {
    		flush();
    	}
    }
}