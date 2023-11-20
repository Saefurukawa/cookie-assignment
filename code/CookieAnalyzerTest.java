package code;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.chart.PieChart.Data;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Unit Testing for CookieAnalyzer
 */
public class CookieAnalyzerTest {
    private CookieAnalyzer cookieAnalyzer;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setup() {
        // Redirect System.out to capture the printed output
        System.setOut(new PrintStream(outContent));
        cookieAnalyzer = new CookieAnalyzer("cookie_log.csv");
    }

    @AfterEach
    public void restoreStreams() {
        // Restore System.out
        System.setOut(originalOut);
    }

    @Test
    /**
     * Check if the file is parsed correctly
     */
    public void testGetData(){
        DataSet data = cookieAnalyzer.getData();
        // Define the date format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Define the time format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        

        //check the length
        assertEquals(19, data.getData().size());

        //check one of the content
        CookieData cData = data.getData().get(0); //AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00
        LocalDate date = LocalDate.parse("2018-12-09", dateFormatter);
        LocalTime time = LocalTime.parse("14:19:00", timeFormatter);

        assertEquals("AtY0laUfhglK3lC7", cData.getCookie()); 
        assertEquals(date, cData.getDate());
        assertEquals(time, cData.getTime());

    }
    @Test
    /**
     * Test where cookie exists
     */
    public void testGetMostActiveCookie() {
        // Get results
        List<String> results1 = cookieAnalyzer.getMostActiveCookie("2018-12-09");
        List<String> results2 = cookieAnalyzer.getMostActiveCookie("2018-12-08");
        List<String> results3 = cookieAnalyzer.getMostActiveCookie("2018-12-06");

        // Assert the results based on expectations
        //first one
        assertEquals(1, results1.size());
        assertTrue(results1.contains("AtY0laUfhglK3lC7"));

        //second one
        assertEquals(3, results2.size());
        assertTrue(results2.contains("SAZuXPGUrfbcn5UA"));
        assertTrue(results2.contains("4sMM2LxV07bPJzwf"));
        assertTrue(results2.contains("fbcn5UAVanZf6UtG"));
      

        // third one
        assertEquals(1, results3.size());
        assertTrue(results3.contains("4sMM2LxV07bPJzwf"));
    }

    @Test
    /**
     * Test case where there are no active cookies for the specified date
     */
    public void testGetMostActiveCookieNoResults() {
        List<String> results = cookieAnalyzer.getMostActiveCookie("2023-01-02");

        // Assert that the result list is empty
        assertTrue(results.isEmpty());
    }  

    @Test
     /**
      * Test case where an invalid date is provided
      */
    public void testGetMostActiveCookieInvalidDate() {
        List<String> results = cookieAnalyzer.getMostActiveCookie("invalid-date");

        // Assert that the result list is empty due to parsing error
        assertTrue(results.isEmpty());
        assertEquals("erorr parsing the date", outContent.toString().trim());
    }
}
