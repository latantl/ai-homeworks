import java.lang.reflect.Array;
import java.util.ArrayList;

class Maze {

    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;
    private static final int ITEM = 4;

    static int ITEMS_LEFT;
    private static Field ROOT;
    private static Field TARGET;

    static void solve(ArrayList<int[]> codes, int numberOfItems) {
        build(codes, numberOfItems);
        tell();
    }

    private static void tell() {
        Field actual = ROOT;
        actual.discover(null);
        while(true) {
            Field next = actual.getNeighbour();
            if (next != null) {
                next.discover(actual);
                actual = next;
                System.out.println(actual);
                continue;
            }
            if (actual != ROOT) {
                actual = actual.previous;
                System.out.println(actual);
                continue;
            }
            break;
        }
        ArrayList<Field> fields_to_target = new ArrayList<>();
        actual = TARGET;
        while(actual != ROOT) {
            fields_to_target.add(actual);
            actual = actual.previous;
        }
        for(int i = fields_to_target.size() - 1; i >= 0; i--) {
            System.out.println(fields_to_target.get(i));
        }
    }

    private static void build(ArrayList<int[]> codes, int numberOfItems) {
        ITEMS_LEFT = numberOfItems;
        int rows = codes.size();
        int columns = codes.get(0).length;
        boolean[][][] attributes = new boolean[rows][][];
        Field[][] fields = new Field[rows][];
        for(int i = 0; i < rows; i++) {
            attributes[i] = new boolean[columns][];
            fields[i] = new Field[columns];
            for (int j = 0; j < columns; j++) {
                attributes[i][j] = to5Bit(codes.get(i)[j]);
                if(attributes[i][j][ITEM]) {
                    fields[i][j] = new ItemField(i, j);
                } else
                    fields[i][j] = new Field(i, j);
            }
        }
        ROOT = fields[0][0];
        TARGET = fields[rows - 1][columns - 1];
        // elso oszlop osszekotese
        for(int i = 1; i < rows; i++) {
            if (!attributes[i][0][NORTH] && !attributes[i - 1][0][SOUTH]) {
                fields[i][0].neighbours.add(fields[i - 1][0]);
                fields[i - 1][0].neighbours.add(fields[i][0]);
            }
        }
        // elso sor osszekotese
        for(int i = 1; i < columns; i++) {
            if (!attributes[0][i][WEST] && !attributes[0][i - 1][EAST]) {
                fields[0][i].neighbours.add(fields[0][i - 1]);
                fields[0][i - 1].neighbours.add(fields[0][i]);
            }
        }
        // nem elso elemek összekotese
        for(int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                if (!attributes[i][j][NORTH] && !attributes[i - 1][j][SOUTH]) {
                    fields[i][j].neighbours.add(fields[i - 1][j]);
                    fields[i - 1][j].neighbours.add(fields[i][j]);
                }
                if (!attributes[i][j][WEST] && !attributes[i][j - 1][EAST]) {
                    fields[i][j].neighbours.add(fields[i][j - 1]);
                    fields[i][j - 1].neighbours.add(fields[i][j]);
                }
            }
        }
    }

    private static boolean[] to5Bit(int code) {
        code %= 32;
        boolean[] result = new boolean[5];
        int ri = 4;
        for(int i = 16; i > 0; i /= 2) {
            result[ri] = code >= i;
            if(result[ri])
                code -= i;
            ri--;
        }
        return result;
    }

}
