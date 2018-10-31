
public class ItemField extends Field {

    private static final String TAKE = "felvesz";
    private boolean has;

    ItemField(int i, int j) {
        super(i, j);
        this.has = true;
    }

    @Override
    public String toString() {
        if(this.has) {
            has = false;
            Maze.ITEMS_LEFT--;
            return super.toString() + "\n" + TAKE;
        }
        return super.toString();
    }
}
