import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Main {

    private static final Pattern SPLITTER = Pattern.compile("(\t| )");
    private static final int SAMPLE_COUNT = 80000;
    private static final int DOC_COUNT = 20000;


    private static final int[][] DOCUMENTS = new int[DOC_COUNT][];

    public static void main(String[] args) {
        readInput();
        for (int[] words : DOCUMENTS) {
            double p = 1;
            double n = 1;
            for (int word : words) {
                Word a = searchAttribute(word);
                p *= a.p();
                n *= a.n();
            }
            System.out.println(p > n ? 1 : 0);
        }
    }

    private static void readInput() {
        Scanner reader = new Scanner(System.in);
        String[][] samples = new String[SAMPLE_COUNT][];
        for (int i = 0; i < SAMPLE_COUNT; i++) {
            samples[i] = SPLITTER.split(reader.nextLine());
        }
        int[] tags = new int[SAMPLE_COUNT];
        for (int i = 0; i < SAMPLE_COUNT; i++) {
            tags[i] = Integer.parseInt(reader.nextLine());
        }
        String[][] docs = new String[DOC_COUNT][];
        for (int i = 0; i < DOC_COUNT; i++) {
            docs[i] = SPLITTER.split(reader.nextLine());
        }
        reader.close();

        for (int i = 0; i < SAMPLE_COUNT; i++) {
            Sentence sentence = new Sentence(tags[i] == 1);
            Sentence.SENTENCES.add(sentence);
            for (String s : samples[i]) {
                try {
                    int word = Integer.parseInt(s);
                    Word w = searchAttribute(word);
                    if (w == null) Word.WORDS.add(w = new Word(word));
                    w.addToSentence(sentence);
                } catch (NumberFormatException e) {}
            }
        }
        for (int i = 0; i < DOC_COUNT; i++) {
            ArrayList<Integer> ints = new ArrayList<>();
            for (String s : docs[i]) {
                try { ints.add(Integer.parseInt(s)); }
                catch (NumberFormatException e) {}
            }
            DOCUMENTS[i] = new int[ints.size()];
            for(int j = 0; j < ints.size(); j++)
                DOCUMENTS[i][j] = ints.get(j);
        }
    }

    private static Word searchAttribute(int id) {
        for (Word a : Word.WORDS)
            if (a.getId() == id)
                return a;
        return null;
    }

}