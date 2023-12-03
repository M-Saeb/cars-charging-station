package byteStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class ByteStreamHandler extends StreamHandler
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
		super.close();
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
        byte[] logBytes = getFormatter().format(arg0).getBytes();
        super.publish(arg0);
    	try {
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