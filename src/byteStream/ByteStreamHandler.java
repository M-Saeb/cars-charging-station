package byteStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.Date;

public class ByteStreamHandler extends Handler
{
	FileOutputStream file = null;
	
    public ByteStreamHandler(String pattern) {
        // create/open log file (name it using provided pattern)
    	 try {
    		File tempFile = new File(pattern); 
    		if(tempFile.exists())
    		{
    			this.file = new FileOutputStream(pattern + ".txt", true);
    		}
    		else 
    		{
				try {
					boolean createFile = tempFile.createNewFile();
					if(createFile)
					{
						this.file = new FileOutputStream(pattern + ".txt", true);
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
        Date timestamp = new Date();
        String timestampString = "[" + timestamp.toString() + "] ";
	    
        byte[] logBytes = getFormatter().format(arg0).getBytes();
        
    	try {
        	for(int i = 0; i < 20; i++)
        	{
        		file.write(45);
        	}
			file.write(10);
	    	file.write(timestampString.getBytes());
	    	file.write(10);
        	/* Add the exact text to add into the log */
	    	file.write(logBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally {
    		flush();
    		close();
    	}
    }
}