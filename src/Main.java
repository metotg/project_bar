
import java.util.Scanner;
import java.util.*;

public class Main {
    private static List<String> barNames = new ArrayList<>();
    private static List<Integer> barLocations = new ArrayList<>();
    private static List<String> barOpeningHours = new ArrayList<>();
    private static int userLocation;

    public static void main(String[] args) {
        initializeBars();
        getUserLocation();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    listAllBars();
                    break;
                case 2:
                    listOpenBars();
                    break;
                case 3:
                    showMap();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private static void initializeBars() {
        barNames.add("Bar 1");
        barLocations.add(10);
        barOpeningHours.add("10:00 - 02:00");

        barNames.add("Bar 2");
        barLocations.add(25);
        barOpeningHours.add("12:00 - 04:00");

        barNames.add("Bar 3");
        barLocations.add(50);
        barOpeningHours.add("18:00 - 03:00");

        // Add more bars here
    }

    private static void getUserLocation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your location (1-100):");
        userLocation = scanner.nextInt();
    }

    private static void displayMenu() {
        System.out.println("Select an option:");
        System.out.println("1. LIST ALL");
        System.out.println("2. LIST OPEN");
        System.out.println("3. MAP");
        System.out.println("4. Exit");
    }

    private static void listAllBars() {
        List<Integer> sortedIndexes = new ArrayList<>();
        for (int i = 0; i < barNames.size(); i++) {
            sortedIndexes.add(i);
        }

        sortedIndexes.sort((index1, index2) ->
                Integer.compare(Math.abs(barLocations.get(index1) - userLocation),
                        Math.abs(barLocations.get(index2) - userLocation)));

        System.out.println("Bars ordered from closest to farthest:");
        for (int i = 0; i < sortedIndexes.size(); i++) {
            int index = sortedIndexes.get(i);
            int distance = Math.abs(barLocations.get(index) - userLocation);
            System.out.printf("%d. %s (%s) - %dm\n", i + 1, barNames.get(index), barOpeningHours.get(index), distance);
        }
    }

    private static void listOpenBars() {
        List<Integer> openBarIndexes = new ArrayList<>();
        for (int i = 0; i < barNames.size(); i++) {
            String openingHours = barOpeningHours.get(i);
            String[] hours = openingHours.split(" - ");
            String openingTime = hours[0];
            String closingTime = hours[1];

            Calendar now = Calendar.getInstance();
            int currentHour = now.get(Calendar.HOUR_OF_DAY);
            int currentMinute = now.get(Calendar.MINUTE);

            String[] openingHourSplit = openingTime.split(":");
            int openingHour = Integer.parseInt(openingHourSplit[0]);
            int openingMinute = Integer.parseInt(openingHourSplit[1]);

            String[] closingHourSplit = closingTime.split(":");
            int closingHour = Integer.parseInt(closingHourSplit[0]);
            int closingMinute = Integer.parseInt(closingHourSplit[1]);

            if (currentHour > openingHour || (currentHour == openingHour && currentMinute >= openingMinute)) {
                if (currentHour < closingHour || (currentHour == closingHour && currentMinute < closingMinute)) {
                    openBarIndexes.add(i);
                }
            }
        }

        openBarIndexes.sort((index1, index2) ->
                barOpeningHours.get(index1).split(" - ")[1].compareTo(barOpeningHours.get(index2).split(" - ")[1]));

        System.out.println("Open bars (ordered by closing time):");
        for (int i = 0; i < openBarIndexes.size(); i++) {
            int index = openBarIndexes.get(i);
            System.out.printf("%d. %s (%s)\n", i + 1, barNames.get(index), barOpeningHours.get(index));
        }
    }

    private static void showMap() {
        StringBuilder mapBuilder = new StringBuilder();
        for (int i = 1; i <= 100; i++) {
            if (i == userLocation) {
                mapBuilder.append("X");
            } else if (barLocations.contains(i)) {
                mapBuilder.append("#");
            } else {
                mapBuilder.append("-");
            }
        }
        System.out.println("Map:");
        System.out.println(mapBuilder.toString());

        System.out.println("Bar List:");
        for (int i = 0; i < barNames.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, barNames.get(i));
        }
    }
}