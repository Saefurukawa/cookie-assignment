package code;

import java.time.LocalDate;
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Implementing CookieData class (each cookie object)
 */
public class CookieData {
    private String cookie;
    private LocalDate date;
    private LocalTime time;

    /**
     * Constructor building cookie
     * @param cookie
     * @param dateString
     * @param timeString
     * @param durationString
     */
    public CookieData(String cookie, String dateString, String timeString){
        this.cookie = cookie;
        // Define the date format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Define the time format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        try{
            // Parse the string into LocalDate object
        LocalDate date = LocalDate.parse(dateString, dateFormatter);
        this.date = date;
        // Parse the string into a LocalTime object
        LocalTime localTime = LocalTime.parse(timeString, timeFormatter);
        this.time = localTime;

        } catch(DateTimeParseException e){
            System.err.println("erorr parsing the date/time");
        }

    }
    /**
     * Return Cookie value
     * @return cookie
     */
    public String getCookie(){
        return cookie;
    }
    /**
     * Return Date 
     * @return date
     */
    public LocalDate getDate(){
        return date;
    }
    /**
     * Return Time
     * @return time
     */
    public LocalTime getTime(){
        return time;
    }
}
