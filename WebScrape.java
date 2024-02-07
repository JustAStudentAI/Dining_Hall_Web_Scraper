package webscrape;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;



/* Web-scraper for UMD dining halls (South Campus, The Y, 251 North)
@since Jan 26, 2024
 */




/**
 *    __/\\\________/\\\_        __/\\\\____________/\\\\_        __/\\\\\\\\\\\\____
 *     _\/\\\_______\/\\\_        _\/\\\\\\________/\\\\\\_        _\/\\\////////\\\__
 *      _\/\\\_______\/\\\_        _\/\\\//\\\____/\\\//\\\_        _\/\\\______\//\\\_
 *       _\/\\\_______\/\\\_        _\/\\\\///\\\/\\\/_\/\\\_        _\/\\\_______\/\\\_
 *        _\/\\\_______\/\\\_        _\/\\\__\///\\\/___\/\\\_        _\/\\\_______\/\\\_
 *         _\/\\\_______\/\\\_        _\/\\\____\///_____\/\\\_        _\/\\\_______\/\\\_
 *          _\//\\\______/\\\__        _\/\\\_____________\/\\\_        _\/\\\_______/\\\__
 *           __\///\\\\\\\\\/___        _\/\\\_____________\/\\\_        _\/\\\\\\\\\\\\/___
 *            ____\/////////_____        _\///______________\///__        _\////////////_____
 **/




public class WebScrape {
    public static void main(String[] args) {


        // Get current date so the URL automatically adjusts
        Date date = new Date();

        int month = date.getMonth() + 1;
        int day = date.getDate();
        int year = date.getYear() + 1900;
        int week = date.getDay();
        boolean weekday = week != 0 && week != 6;
        String keyword = "";
        int hall;
        int meal;
        // Lengths used to trim String of titles to print out
        final int TITLE_START = 23;
        // This variable is for counting the number of breakfast counts to discern breakfast/lunch/dinner
        int numOfOccurrences = 0;


        Scanner scan = new Scanner(System.in);
        // Let user choose which dining hall they want
        do {
            System.out.println("Choose an option: \n1: South Campus \n2: Yhentamitsi \n3: 251 North");
            hall = scan.nextInt();
        } while(hall > 3 || hall < 1);


        // Reassign hall for the URL location & get keyword used in that specific hall
        if(hall == 1) { // south campus
            hall = 16;
            keyword = "Broiler Works";
        } else if(hall == 2) { // yhentamistsi
            hall = 19;
            keyword = "Breakfast";
        } else { // 251 north
            keyword = "Smash Burger";  // This changes in lunch and dinner for some reason
            hall = 51;
        }


        // Let user choose between meals they want to see
        do {
            if(weekday || hall == 51) {
                System.out.println("Choose an option: \n0: All \n1: Breakfast \n2: Lunch \n3: Dinner");
            } else {
                System.out.println("Choose an option: \n0: All \n1: Brunch \n2: Dinner");
            }
            meal = scan.nextInt();
        } while((weekday & meal > 3) || (!weekday & meal > 2) || meal < 0);


        // URL for UMD yahentamitsi dining hall menu for current date
        final String URL = "https://nutrition.umd.edu/?locationNum=" + hall + "&dtdate=" + month + "/" + day + "/" + year;


        try {
            // Create the document menu from the URL
            final Document document = Jsoup.connect(URL).get();

            // Get the cards (contains title of food and all items within it)
            Elements cards = document.select("div.card");


            /*
            /
            /
            /
             */
            // Each card is a section that contains a title and food items
            // We will go into each of them to retrieve info
            for(Element card : cards) {
                // Get title
                String title = card.select("h5.card-title").toString();
                title = title.substring(TITLE_START,title.length() - 5);
                // Check if current title is keyword
                if(title.equals(keyword)) {
                    // Found a title so increment
                    numOfOccurrences++;
                    // Change keyword if 251 north
                    if(hall == 51 & numOfOccurrences == 1) {
                        keyword = "Smash Deli";  // Reassign b/c it changes on the website
                        title = "Smash Deli";    // Have to do this to call printMealTime()
                    }
                }

                if(meal == 1) {
                    if(numOfOccurrences > 1) { // past first meal, no need to go to second meal
                        break;
                    }
                    System.out.println(title);
                } else if(meal == 2) {
                    if(numOfOccurrences == 2) { // currently in second meal
                        System.out.println(title);
                    } else if(numOfOccurrences > 2) { // in Dinner for weekdays now, no need to continue
                        break;
                    } else {
                        continue;  // continue if we are still in breakfast
                    }
                } else if(meal == 3){
                    if(numOfOccurrences == 3) { // In last meal
                        System.out.println(title);
                    } else {
                        continue;
                    }
                } else { // all meals
                    if(title.equals(keyword)) {
                        // Call to print which meal it is
                        printMealTime(weekday, numOfOccurrences, hall);
                    }
                    System.out.println(title);
                }

                // Goes through the food items in each card
                Elements foods = card.select("a.menu-item-name");
                for(Element food : foods) {
                    // Gets food name
                    String foodPrint = food.toString();
                    int startIndex = foodPrint.indexOf(">");
                    foodPrint = foodPrint.substring(startIndex + 1, foodPrint.length() - 4);
                    foodPrint = foodPrint.replace("&amp;", "&");
                    System.out.println(foodPrint); // Prints food
                }
                System.out.println(); // New line
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    /* This function is for printing out which current meal is showing when user chooses to display all
    @param weekday, weather it's a weekday or not
    @param numOfOccurrences, number of times keyword appeared
    @param hall, which hall the user picked
     */
    public static void printMealTime(boolean weekday, int numOfOccurrences, int hall) {
        if(hall == 51 || weekday) { // prints all meals
            switch (numOfOccurrences) {
                case 1 -> System.out.println("--BREAKFAST--");
                case 2 -> System.out.println("--LUNCH--");
                case 3 -> System.out.println("--DINNER--");
            }
        } else { // weekend and not 251 north
            switch (numOfOccurrences) {
                case 1:
                    System.out.println("--BRUNCH--");
                    break;
                case 2:
                    System.out.println("--DINNER--");
                    break;
            }
        }
    }
}