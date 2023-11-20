package Record;

import java.util.Objects;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
public class Record
{
    public enum l_enumFileCreation
    {
        CREATED,
        EXISTING,
        ERROR
    }
    /* Counter to keep track of existing files */
    int l_intExistingFileCounter = 0;
    /*
    Create a folder to send the .txt files
     */
    l_enumFileCreation l_enumFolderCreationOption = FolderCreation();
    /*
    Logic for creating the files into folder, in this logic a counter is reset before entering (this for each code iteration)
    It keeps going unless we get a successful file creation
     */

    do
    {
        l_intExistingFileCounter++;
        try {
            l_enumFolderCreationOption = CreateFile(l_intExistingFileCounter);
        } catch (IOException e) {
            System.out.println("File could not be created...");
        }
    }while((l_enumFolderCreationOption != l_enumFileCreation.CREATED));
    static l_enumFileCreation FolderCreation()
    {
        /* Variable for return possible values */
        l_enumFileCreation l_varReturnValue = l_enumFileCreation.ERROR;
        /* getProperty -> is used to obtain the current directory that the project is being executed */
        String l_strCurrentDir = System.getProperty("user.dir");
        /* Folder name to separate all the generated '.txt' files */
        String l_strFolderName = "GenTxt";
        /* Full new path */
        String l_strFolderPath = l_strCurrentDir + File.separator + l_strFolderName;
        /* Object for new folder creation */
        File l_fldrNewFolder = new File(l_strFolderPath);

        if (!l_fldrNewFolder.exists()) {
            if (l_fldrNewFolder.mkdirs()) {
                l_varReturnValue = l_enumFileCreation.CREATED;
            } else {
                l_varReturnValue = l_enumFileCreation.ERROR;
            }

        } else {
            /* Folder already existing */
            l_varReturnValue = l_enumFileCreation.EXISTING;
        }
        return l_varReturnValue;
    }
    static l_enumFileCreation CreateFile(int varFileCounter) throws IOException
    {
        /* Variable for return possible values */
        l_enumFileCreation l_varReturnValue = l_enumFileCreation.ERROR;
        /* getProperty -> is used to obtain the current directory that the project is being executed */
        String l_strCurrentDir = System.getProperty("user.dir");
        /* Folder name to separate all the generated '.txt' files */
        String l_strFolderName = "GenTxt";
        /* File type extension */
        String l_strSufix = ".txt";
        /* Full new path */
        String l_strFilePath = l_strCurrentDir + File.separator + l_strFolderName + File.separator + varManufacture.get_enumVehicleType().name() + Integer.toString(varFileCounter);


        File file2createBrand = new File(l_strFilePath + l_strSufix);
        if (file2createBrand.createNewFile())
        {
            System.out.println("File created as... " + file2createBrand.getName());
            try
            {
                /*
                Open file already created to write expected information
                 */
                FileWriter file2writeBrand = new FileWriter(l_strFilePath + l_strSufix);
                /* Create object buffered so multiple information can be stored */
                BufferedWriter info = new BufferedWriter(file2writeBrand);

                /* Add info to buffer and write into file */
                info.write("-------------------------------------");
                info.newLine();
                info.write("-> Vehicle Type... ");
                info.newLine();

                /* Send info to .txt file and close file */
                info.close();
                System.out.println("Successfully write in file " + file2createBrand.getName());
                l_varReturnValue = l_enumFileCreation.CREATED;
            }catch (IOException e)
            {
                System.out.println("An error ocurred");
                l_varReturnValue = l_enumFileCreation.ERROR;
            }
        } else
        {
            System.out.println("File already existed");
            l_varReturnValue = l_enumFileCreation.EXISTING;
        }
        return l_varReturnValue;
    }
}
