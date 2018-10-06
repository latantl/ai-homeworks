import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        readInput();
    }

    private static Maze readInput() {
        ArrayList<int[]> rows = new ArrayList<>();
        Scanner reader = new Scanner(System.in);
        while(reader.hasNextLine()) {
            String[] str_elems = reader.nextLine().split(" ");
            int[] elems = new int[str_elems.length];
            for(int i = 0; i < str_elems.length; i++)
                elems[i] = Integer.parseInt(str_elems[i]);
            rows.add(elems);
        }
        int n_of_items = rows.remove(rows.size() - 1)[0];
        return new Maze(rows, n_of_items);
    }

}
