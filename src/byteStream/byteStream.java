package byteStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
	public void logByteStream(String originalFile, String destinationFile) throws IOException
	{
	    FileInputStream input = null;
	    FileOutputStream output = null;
	    
	    /* TODO: add local path where files will be stored */
		try {
			input = new FileInputStream(originalFile + ".txt");
			output = new FileOutputStream(destinationFile + "_backup" + ".txt");
		    int c;
		
		    while ((c = input.read()) != -1) {
		    	output.write(c);
		    }
		} finally {
		    if (input != null) {
		    	input.close();
		    }
		    if (output != null) {
		    	output.close();
		    }
		}
	}

}
