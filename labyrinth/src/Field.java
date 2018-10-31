import java.util.ArrayList;

public class Field {

    public ArrayList<Field> neighbours = new ArrayList<>(4);
    private boolean isDiscovered = false;
    Field previous = null;

    private String name;

    Field(int i, int j) {
        this.name = i + " " + j;
    }

    Field getNeighbour() {
        for (Field n : neighbours) {
            if (!n.isDiscovered) {
                return n;
            }
        }
        return null;
    }

    void discover(Field previous) {
        this.previous = previous;
        this.isDiscovered = true;
    }

    @Override
    public String toString() {
        return name;
    }
}
