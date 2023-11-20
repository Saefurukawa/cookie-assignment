package code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Implementing DataSet class
 */
public class DataSet{
    private ArrayList<CookieData> data = new ArrayList<CookieData>();

    /**
     * Public constructor reading file content
     * @param filename
     */
    public DataSet(String filename){
        
        try {
            String filepath = "./data/" + filename;
			BufferedReader in = new BufferedReader(new FileReader(filepath));
            String line;
            in.readLine(); //ignore the first line (cookies, timestamp)

            //iterate through each line
			while ((line = in.readLine()) !=null){
                processLine(line);
            }
            in.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}catch (IOException e) {
			System.out.println("erorr parsing the file");
        }
    }

    /**
     * Process each line in the file
     * @param line
     */
    private void processLine(String line){
        String[] parts = line.split(",");
        //error handling
        if (parts.length<2){
            System.err.println("error parsing the logged file");
            return;
        }

        String cookie = parts[0].trim();  // cookie string
        String part2 = parts[1].trim();  // the rest

        String[] dateTimeParts = part2.split("T");
        //error handling
        if (dateTimeParts.length<2){
            System.err.println("error parsing the logged file");
            return;
        }

        String datePart = dateTimeParts[0]; //date part
        String timePart = dateTimeParts[1]; //the rest

        String []timeParts = timePart.split("\\+");
        //error handling
        if (timeParts.length<2){
            System.err.println("error parsing the logged file");
            return;
        }

        String localTime = timeParts[0]; //local time part
        //String offset = timeParts[1]; //offset part

        //make a cookiedata object
        CookieData cookieData = new CookieData(cookie, datePart, localTime);
        data.add(cookieData);

    }
    /**
     * Get data
     * @return data
     */
    public ArrayList<CookieData> getData(){
        return data;
    }

    /**
     * Print data
     * @return str
     */
    public String printData(){
        String str = "";
        for (CookieData item: data){
            str += item.getCookie() + " " + item.getDate() + " " + item.getTime() + "\n";
        }
        return str;
    }
    
}