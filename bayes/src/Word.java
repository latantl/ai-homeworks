import java.util.ArrayList;

public class Word {

    public static final ArrayList<Word> WORDS = new ArrayList<>();

    private int id;
    private ArrayList<Sentence> positiveSentences = new ArrayList<>();
    private ArrayList<Sentence> negativeSentences = new ArrayList<>();

    Word(int id) { this.id = id; }

    public void addToSentence(Sentence sentence) {
        if (sentence.isPositive()) positiveSentences.add(sentence);
        else negativeSentences.add(sentence);
        sentence.add(this);
    }

    public int getId() {
        return id;
    }

    private double sum() {
        return positiveSentences.size() + negativeSentences.size();
    }
    public double p() {
        return positiveSentences.size() / sum();
    }
    public double n() {
        return negativeSentences.size() / sum();
    }
}