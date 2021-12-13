package dist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class Clean {

    public static void main(String[] args) {
        String filterWordsFilename      = "./words.txt";
        File filterFile                 = new File(filterWordsFilename);
        String comparisonWordsFilename  = "./dutchWords.txt";
        File comparisonFile             = new File(comparisonWordsFilename);
        String comparisonWordsFilename2 = "./dutchWords2.txt";
        File comparisonFile2            = new File(comparisonWordsFilename2);

        HashSet<String> words = new HashSet<>();
        HashSet<String> dutchWords = new HashSet<>();

        int noOfWords = 0;
        int noOfDutchWords = 0;
        int noOfAddedDutchWords = 0;

        try {
            Scanner sc = new Scanner(filterFile);
            while (sc.hasNext()){
                String s = sc.next();
                words.add(s);
                noOfWords++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(noOfWords);

        try {
            Scanner sc = new Scanner(comparisonFile);
            while (sc.hasNext()){
                String s = sc.next();
                noOfDutchWords++;
                if (!words.contains(s)){
                    dutchWords.add(s);
                    noOfAddedDutchWords++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(noOfDutchWords);
        System.out.println(noOfAddedDutchWords);

        try {
            FileWriter wr = new FileWriter(comparisonFile2);
            Stream<String> stream = dutchWords.stream();
            stream.forEach(a -> {
                try {
                    wr.write(a);
                    wr.write("\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            wr.flush();
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
