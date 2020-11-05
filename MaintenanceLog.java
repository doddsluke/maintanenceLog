package maintenancelog;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


//Class to hold all interactions with logs
public class MaintenanceLog 
{
    ArrayList<String[]> currentLog;
    Scanner consoleInput;
    
    //Contructor loads all logs currently in file and creates new scanner
    public MaintenanceLog() 
    {
        currentLog =  getLogEntries();
        consoleInput = new Scanner(System.in);
    }
 
    //function which gets all logs currently in text file 
    public ArrayList<String[]> getLogEntries()
    {
        try 
        {   
            ArrayList<String[]> logArray = new ArrayList<String[]>();
            
            File logFile = new File(System.getProperty("user.dir") + "/src/maintenancelog/LogTextFile.txt");
            Scanner logFileReader = new Scanner(logFile);
            while (logFileReader.hasNextLine()) 
            {
                String [] logLine = logFileReader.nextLine().split(" ");
                logArray.add(logLine);
            }
        
            logFileReader.close();
            return logArray;
      
        } 
        catch (Exception e) 
        {
            System.out.println("An error occurred.");
        }
        return null;
    }
    
    //Function to save logs to text file
    public void saveLogs()
    {
        try
        {
            FileWriter logWriter = new FileWriter(System.getProperty("user.dir") + "/src/maintenancelog/LogTextFile.txt");
        
            
            for (int i = 0; i < currentLog.size(); i ++)
            {
                for (int j = 0; j < currentLog.get(i).length; j ++)
                {
                    logWriter.write(currentLog.get(i)[j] + " ");
                }
                logWriter.write("\n");
            }
            logWriter.close();
        }
        catch (Exception e)
        {
            System.out.println("An error occured");
        }
    }
    
    //Function to allow the user to add a new log entry
    public void newLogEntry()
    {
        String assetID;
        String hours = "";
        String date;
        System.out.println("=========================================================");
        System.out.println("Please input the new log: ");
        System.out.println();
        System.out.println("Please enter the asset id (must be 6 characters long and begin with M,P,T or O");
        assetID = consoleInput.nextLine();
        
        
        try
        {
             if (Character.toUpperCase(assetID.charAt(0)) != 'M' && Character.toUpperCase(assetID.charAt(0)) != 'P' && 
                Character.toUpperCase(assetID.charAt(0)) != 'T' && Character.toUpperCase(assetID.charAt(0)) != 'O')
            {
                System.out.println("Asset ID was not in correct format");
                System.out.println("=========================================================");
                newLogEntry();
            }
            else
            {
                
                if (assetID.length() != 6)
                {
                    System.out.println("Asset ID was not correct length");
                    System.out.println("=========================================================");
                    newLogEntry();
                }
                else
                {
                    System.out.println("Please enter hours spent on maintenance: ");
                
                    hours = consoleInput.nextLine();
                    
                    Integer.parseInt(hours); //Whole number check
         
         
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");  
                    LocalDateTime now = LocalDateTime.now();  
                    date = dtf.format(now);  
                    currentLog.add(new String [] {date,assetID,hours});
                }
                
            }
        
        }
        catch (Exception e)
        {
            System.out.println("An error occured in your inputs, please re enter log");
            System.out.println("=========================================================");
            mainMenu();
        }
        
                

        
        
    }
    
    //Function to loop through all logs and display them
    public void displayLogs()
    {
        System.out.println("Displaying logs: ");
        for (int i = 0; i < currentLog.size(); i ++)
        {
            for (int j = 0; j < currentLog.get(i).length; j ++)
            {
                System.out.print(currentLog.get(i)[j] + " ");
            }
            System.out.println();
        }

    }
    
    
    //Allows the user to edit a log
    public void editLog()
    {
        int logOption;
        System.out.println("Please enter the number corrosponding to the log you wish to edit");
        
        for (int i = 0; i < currentLog.size(); i++)
        {
            System.out.print(i + ": ");
            for (int j = 0; j < currentLog.get(i).length; j++)
            {
                System.out.print(currentLog.get(i)[j] + " ");
            }
            System.out.println();
        }
        
        try 
        {
            logOption = consoleInput.nextInt();
            consoleInput.nextLine();
            
            if (logOption > -1 && logOption < currentLog.size())
            {
                System.out.println("Current Log:");
                
                for (int i = 0; i < currentLog.get(logOption).length; i++)
                {
                    System.out.print(currentLog.get(logOption)[i] + " ");
                }
                System.out.println();
                newLogEntry();
                currentLog.remove(logOption);
                System.out.println("Entry has been edited");
            }
            else
            {
                System.out.println("Invalid option, retry");
                editLog();
            }
             
            
        }
        catch (Exception e)
        {
            System.out.println("An error occured with your input, please try again");
            mainMenu();
        }
        
        
    }
    
    //The main menu of the system
    public void mainMenu()
    {
        
        boolean running = true;
        while (running)
        {
            System.out.println("===============================");
            System.out.println("===========Main Menu===========");
            System.out.println("===============================");


            System.out.println();

            System.out.println("Please enter the number corresponding to your option");
            System.out.println();
            System.out.println("1: Display Maintenance Logs");
            System.out.println("2: New Log Entry");
            System.out.println("3: Edit Existing Log");
            System.out.println("4: Delete Existing Log");
            System.out.println("5: Generate Summary Info");
            System.out.println("6: Save Changes");
            System.out.println("7: Exit System");


            String menuOption = consoleInput.nextLine();

            switch (menuOption)
            {
                case "1":
                    displayLogs();
                    break;
                    
                case "2":
                    newLogEntry();
                    System.out.println("Entry has been added");
                    break;
                    
                case "3":
                    editLog();
                    break;
                    
                case "4":
                    deleteLog();
                    break;
                    
                case "5":
                    generateSummary();
                    break;
                    
                case "6":
                    saveLogs();
                    System.out.println("Maintenance logs have been saved to LogTextFile");
                    break;
                    
                case "7":
                    running = false; 
                    break;
                    
                default:
                    System.out.println("Invalid input for main menu, please retry");
                    mainMenu();    
                    break;
            }


           
        }
        
    }
    
    //Method to delete an existing log
    public void deleteLog()
    {
     
        int logOption;
        System.out.println("Please enter the number corrosponding to the log you wish to delete");
        
        for (int i = 0; i < currentLog.size(); i++)
        {
            System.out.print(i + ": ");
            for (int j = 0; j < currentLog.get(i).length; j++)
            {
                System.out.print(currentLog.get(i)[j] + " ");
            }
            System.out.println();
        }
        
        try 
        {
            logOption = consoleInput.nextInt();
            consoleInput.nextLine();
            
            if (logOption > -1 && logOption < currentLog.size())
            {
                
               
                currentLog.remove(logOption);
                System.out.println("Log successfully deleted");
                
            }
            else
            {
                System.out.println("Invalid option, retry");
                deleteLog();
            }
             
            
        }
        catch (Exception e)
        {
            System.out.println("An error occured with your input, please try again");
            mainMenu();
        }
        
    }
    
    //Function to generate the summary as required
    public void generateSummary()
    {
        System.out.println("Number of entries: " + currentLog.size());
        double averageTime = 0;
        int count = 0;
        
        for (int i = 0; i < currentLog.size(); i ++)
        {   
            
            averageTime +=Integer.parseInt(currentLog.get(i)[2]);
            
            if (Integer.parseInt(currentLog.get(i)[2]) > 4)
            {
                count++;
            }
            
        }
        
        averageTime = averageTime / currentLog.size();
        
        System.out.println("Average hours spent: " + averageTime);
        System.out.println("Count of tasks which took more than 4 hours: " +count);
        
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        //Creates new object and starts at main menu
        MaintenanceLog ml = new MaintenanceLog();
        ml.mainMenu();
    }
    
}
