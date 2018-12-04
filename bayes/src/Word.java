import java.util.*;

public class Word {
    private static final int WORD_FREQUENT_BOUND = 6;
    private static final int COMPOSITE_FREQUENT_BOUND = 3;
    private static final int POSITIVE = 1;
    private static final int NEGATIVE = 0;
    private static Map<Long, Word> WORDS = new HashMap<>();
    private static Map<Long, Word> COMPOSITES = new HashMap<>();

    private double sentenceCount;
    private double positive;

    private Word() { }

    public static void addSentence(ArrayList<Long> ids, int result) {
        if (ids.size() == 0) return;
        addWord(ids.get(0), result);
        for(int i = 1; i < ids.size(); i++) {
            addWord(ids.get(i), result);
            addComposite(ids.get(i), ids.get(i - 1), result);
        }
    }
    
    private static void addWord(long id, int result) {
        Word w = WORDS.get(id);
        if (w == null) {
            w = new Word();
            WORDS.put(id, w);
        }
        w.addResult(result);
    }
    private static void addComposite(long id1, long id2, int result) {
        long id = id1 * toCompositeId(id1, id2);
        Word comp = COMPOSITES.get(id);
        if (comp == null) {
            comp = new Word();
            COMPOSITES.put(id, comp);
        }
        comp.addResult(result);
    }

    private static long toCompositeId(long id1, long id2) {
        return Long.min(id1, id2) * 1000000 + Long.max(id1, id2);
    }

    public void addResult(int result) {
        sentenceCount++;
        if (result == 1) positive++;
    }
    public double positiveRatio() { return positive / sentenceCount; }

    public static void removeNonFrequents() {
        remove(WORDS, WORD_FREQUENT_BOUND);
        remove(COMPOSITES, COMPOSITE_FREQUENT_BOUND);
    }
    private static void remove(Map<Long, Word> words, int bound) {
        ArrayList<Long> keysToRemove = new ArrayList<>();
        for (Map.Entry<Long, Word> entry : words.entrySet()) {
            Word word = entry.getValue();
            if (word.sentenceCount < bound)
                keysToRemove.add(entry.getKey());
        }
        for (Long key : keysToRemove)
            words.remove(key);
    }

    public static ArrayList<Word> getKnownWordsOf(ArrayList<Long> ids) {
        ArrayList<Word> result = new ArrayList<>();
        for(long id : ids) {
            Word known = WORDS.get(id);
            if (known != null) result.add(known);
        }
        return result;
    }
    public static ArrayList<Word> getKnownCompositesOf(ArrayList<Long> ids) {
        ArrayList<Word> result = new ArrayList<>();
        for(int i = 1; i < ids.size(); i++) {
            long cid = toCompositeId(ids.get(i), ids.get(i - 1));
            Word known = COMPOSITES.get(cid);
            if (known != null) result.add(known);
        }
        return result;
    }

}