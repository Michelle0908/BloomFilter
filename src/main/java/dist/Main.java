package dist;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        String filterWordsFilename      = "./words.txt";
        File filterFile                 = new File(filterWordsFilename);
        String comparisonWordsFilename  = "./dutchWords.txt";
        File comparisonFile             = new File(comparisonWordsFilename);
        Scanner console                 = new Scanner(System.in);

        int numberOfFalsePositive       = 0;
        /*
         * Create Filter
         */
        System.out.println("Create Filter");
        System.out.println("Select P and press Enter");
        Double p = console.nextDouble();
        BloomFilter bloomFilter = new BloomFilter(58110, p);


        /*
         * Initialize Filter with words
         */
        System.out.println("Add Words To Filter");
        try {
            Scanner sc = new Scanner(filterFile);
            while (sc.hasNext()){
                String s = sc.next();
                bloomFilter.addWordToArray(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        /*
         * Filter Comparison List
         */
        System.out.println("Filter with Comparison List");
        try {
            Scanner sc = new Scanner(comparisonFile);
            while (sc.hasNext()){
                String s = sc.next();
                if(bloomFilter.isInFilter(s)) {
                    numberOfFalsePositive++;
                }
            }
            System.out.println("Number of False Positives: " + numberOfFalsePositive);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bloomFilter.getStats();
    }
}
