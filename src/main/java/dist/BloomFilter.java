package dist;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class BloomFilter {


    private int n;                       //erwartete Elemente, n (words.txt : 58110)
    private int m;                       //Filtergrösse, m
    private int k;                       //Anzahl Hashfunktion, k
    private double p;                    //Fehlerwahrscheinlichkeit, p
    private boolean[]   filterArray;       //FilterArray
    private String[]    filterWords;
    private List<HashFunction> hashFunctionList = new ArrayList<>();

    public BloomFilter(int n, double p) {
        this.n = n;
        this.p = p;

        //https://hur.st/bloomfilter/
        //m = ceil((n * log(p)) / log(1 / pow(2, log(2))));
        this.m = (int)((n * Math.log(p)) / Math.log(1 / Math.pow(2, Math.log(2))));
        this.filterArray = new boolean[m];
        this.filterWords = new String[m];

        //k = round((m / n) * log(2));
        this.k = -(int)(Math.log(p) / Math.log(2));

        for (int i = 0; i < k; i++) {
            hashFunctionList.add(Hashing.murmur3_128(i));
        }

    }

    public void addWordToArray(String s) {
        for (int i = 0; i < hashFunctionList.size(); i++) {
            HashCode hashCode = hashFunctionList.get(i).hashString(s, Charset.defaultCharset());
            int index = Math.abs(hashCode.asInt()) % m;
            filterArray[index] = true;
            filterWords[index] = s;
        }
    }

    public boolean isInFilter(String s) {
        for (int i = 0; i < hashFunctionList.size(); i++) {
            HashCode hashCode = hashFunctionList.get(i).hashString(s, Charset.defaultCharset());
            int index = Math.abs(hashCode.asInt()) % m;
            if(!filterArray[index]) return false;
        }
        return true;
    }

    public void getStats(){
        System.out.println("Filter Size: " + filterArray.length);
        System.out.println("Amount Hash Functions: " + hashFunctionList.size());
    }
}
