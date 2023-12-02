package byteStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class byteStream 
{
    /*
    Function: calculateNearestStation
    Description: Calculate the nearest station regarding the auto current location that is given to the function
    Input:
    -originalFile -> name of the original file to create a backup
    -originalFile -> name on how the destination file should be named, as sufix it will always have a "backup"
    Return: Nearest Charging Station ID
     */
	public void logByteStream(String destinationFile, String varString) throws IOException
	{
	    FileOutputStream output = null;
	    
        Date timestamp = new Date();
        String timestampString = "[" + timestamp.toString() + "] ";
	    
        try {
        	output = new FileOutputStream(destinationFile + ".txt", true);

            output.write(10);
        	for(int i = 0; i < 20; i++)
        	{
        		output.write(45);
        	}
        	output.write(10);
        	output.write(timestampString.getBytes());
        	output.write(10);
        	/* Add the exact text to add into the log */
        	output.write(varString.getBytes());
        } finally {
            if (output != null) {
            	output.close();
            }
        }
	}

}
