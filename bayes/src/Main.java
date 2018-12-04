import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern SPLITTER = Pattern.compile("(\t| )");
    private static final int SAMPLE_COUNT = 80000;
    private static final int DOC_COUNT = 20000;

    public static void main(String[] args) {
        String[][] samples = new String[SAMPLE_COUNT][];
        int[] sample_results = new int[SAMPLE_COUNT];

        Scanner reader = new Scanner(System.in);
        for (int i = 0 ; i < SAMPLE_COUNT; i++) {
            samples[i] = SPLITTER.split(reader.nextLine());
        }
        for(int i = 0; i < SAMPLE_COUNT; i++) {
            sample_results[i] = Integer.parseInt(reader.nextLine());
        }
        for (int i = 0; i < SAMPLE_COUNT; i++) {
            ArrayList<Long> ids = new ArrayList<>();
            for (String s : samples[i]) {
                if (!s.equals("")) {
                    ids.add(Long.parseLong(s));
                }
            }
            Word.addSentence(ids, sample_results[i]);
        }

        Word.removeNonFrequents();

        for (int i = 0; i < DOC_COUNT; i++) {
            String[] words = SPLITTER.split(reader.nextLine());
            ArrayList<Long> ids = new ArrayList<>();
            for(String word : words)
                if (!word.equals(""))
                    ids.add(Long.parseLong(word));
            Result result = new Result();
            result.addToResult(Word.getKnownWordsOf(ids));
            result.addToResult(Word.getKnownCompositesOf(ids));
            result.write();
        }
        reader.close();
    }

    private static class Result {
        public double res = 0;
        public void addToResult(ArrayList<Word> words) {
            for (int j = 0; j < words.size(); j++) {
                Word word = words.get(j);
                res += word.positiveRatio() - 0.5;
            }
        }
        public void write() { System.out.println( res > 0 ? 1 : 0 );}
    }

}
