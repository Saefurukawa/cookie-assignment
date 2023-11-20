package code;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementing CookieAnalyzer class to get the most active cookies for a specified day
 */
public class CookieAnalyzer {
    private DataSet dataSet;

    /**
     * Public constructor reading the file 
     * @param filename
     */
    public CookieAnalyzer(String filename){
        this.dataSet = new DataSet(filename);
        //System.out.println(dataSet.printData());

    }
    /**
     * Return the list of most active cookies for that day
     * @param dateString
     * @return cookieList
     */
    public List<String> getMostActiveCookie(String dateString){
        ArrayList<CookieData> dataList = dataSet.getData();
        int maxCount =0;
        ArrayList <String> cookieList = new ArrayList<>();

        // Define the date format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            // Parse the string into a LocalDate object
            LocalDate date = LocalDate.parse(dateString, dateFormatter);
            //Create hashmap to keep track of most active cookies
            HashMap<String, Integer> logCookieHashMap = new HashMap<>();

            //Populate hashmap
            for (CookieData cookie: dataList){
                if (cookie.getDate().equals(date)){
                    String cookieValue = cookie.getCookie();
                    logCookieHashMap.put(cookieValue, logCookieHashMap.getOrDefault(cookieValue, 0)+1);
                    
                }
            }

            //Find the max count, store cookie value in the list
            for (Map.Entry<String, Integer> entry: logCookieHashMap.entrySet()){
                if (entry.getValue()> maxCount){
                    maxCount = entry.getValue();
                    cookieList.clear(); //reset the list
                    cookieList.add(entry.getKey());
                }
                else if (entry.getValue()== maxCount){
                    cookieList.add(entry.getKey()); //we can have multiple cookies
                }
            }
            return cookieList;

        } catch(DateTimeParseException e){
            System.out.println("erorr parsing the date");
            return cookieList;
        }
        
    }

    public static void main(String[] args) {
        if (args.length != 3 || !args[1].equals("-d")) {
            System.exit(1);;
        }
        String filename = args[0]; //output file
        String date = args[2];

        CookieAnalyzer experimenter = new CookieAnalyzer(filename);
        List<String> results = experimenter.getMostActiveCookie(date);
        if (results.isEmpty()){
            System.out.println("No active cookie for " + date);
        }
        else{
            for (String str : results) {
                System.out.println(str);
            }
        }

    }
}
