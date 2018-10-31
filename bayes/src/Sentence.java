import java.util.ArrayList;

public class Sentence {

    public static final ArrayList<Sentence> SENTENCES = new ArrayList<>();

    private ArrayList<Word> words = new ArrayList<>();
    private boolean isPositive;

    Sentence(boolean isPositive) { this.isPositive = isPositive; }

    public void add(Word word) { words.add(word); }

    public boolean isPositive() { return isPositive; }

}
