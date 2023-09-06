import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/*
    Count the occurrences of words in a file using Map
*/
public class WordCounter {
    Map<String, Integer> map;
    String fileName;

    public WordCounter(String fileName) {
        this.fileName = fileName;
        map = new HashMap<String, Integer>();

        //TODO: create a HashMap for map
    }

    public void readFile() throws IOException {
        //read text from file
        FileReader r = new FileReader(fileName);

        //divide the text into words
        StreamTokenizer tok = new StreamTokenizer(r);

        //TODO: update tok to restrict wordChars to
        //      'a' ~ 'z' and 'A' ~ 'Z'
        tok.resetSyntax();
        tok.wordChars('a', 'z');
        tok.wordChars('A', 'Z');

        //read through the file
        while (tok.nextToken() != StreamTokenizer.TT_EOF) {
            if (tok.ttype != StreamTokenizer.TT_WORD)
                continue;

            String word = tok.sval;
            //TODO: add word as a new entry or increase the counter
            if (!map.containsKey(word)) {
                map.put(word, 1);
            } else {
                int count = map.get(word);
                map.put(word, count + 1);
            }
        }

    }

    public void printWords() {
        System.out.println("All words");
        //TODO: print all entries in the form of <word>: <frequency>
        Set<Entry<String, Integer>> entries = map.entrySet();
        for (Entry<String, Integer> e : entries)
            System.out.println("<" + e.getKey() + ">: " + "<" + e.getValue() + ">");
        System.out.println();
        System.out.println("");

        System.out.println("All words sorted by key");
        ArrayList<Entry<String, Integer>> byKey = new ArrayList<>();
        //TODO: sort sort by key
        //      add all entries to byKey
        //      sort byKey using Collections.sort
        for(Entry<String, Integer> e: entries)
            byKey.add(e);
        Collections.sort(byKey,(a, b) -> a.getKey().compareTo(b.getKey()));
        System.out.println("");


        System.out.println("All words sorted by value");
        ArrayList<Entry<String, Integer>> byVal = new ArrayList<>();
        //TODO: sort sort by value
        //      add all entries to byVal
        //      sort byVal using Collections.sort
        System.out.println("");
    }

    public static void main(String[] args) {
        //String fileName = "C:\\Users\\youngmin.kwon\\Documents\\CSE214_Data_Structures\\Recitation\\WordCounter.java";
        String fileName = "WordCounter.java";
        WordCounter wc = new WordCounter(fileName);
        try {
            wc.readFile();
            wc.printWords();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}