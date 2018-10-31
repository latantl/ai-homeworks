import java.util.Scanner;


public class Main {

    private static final int SAMPLE_COUNT = 80000;
    private static final int DOC_COUNT = 20000

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        ArrayList<String> samples = new ArrayList<String>();
        for (int i = 0; i < SAMPLE_COUNT; i++) {
            samples.add(reader.nextLine());
        }

    }

}