import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static Random rand;
    final static int sCount = 17011;
    final static int tCount = 4252;
    final static int fCount = 81;
    final static String sep = "\t";
    static double[][] sIn, tIn;
    static double[] sOut;
    static double iMax, oMax;

    public static void main(String[] args) {
        loadData();
        init();
        for(int n = 0; n < 55; n++)
            for(int i = 0; i < sCount; i++)
                train(sIn[i], sOut[i]);
        for(int i = 0; i < tCount; i++)
            System.out.println(forward(tIn[i])*oMax);
    }
    static InputStream is = System.in;
    static void loadData() {
        try { if(is == null) is = new FileInputStream("test.txt"); } catch (Exception e) {}
        sIn = new double[sCount][];
        sOut = new double[sCount];
        tIn = new double[tCount][];
        Scanner scanner = new Scanner(is);
        for (int i = 0; i < sCount; i++) {
            sIn[i] = fromStrings(scanner.nextLine().split(sep));
            iMax = Math.max(iMax, amax(sIn[i]));
        }
        for (int i = 0; i < sCount; i++)
            sOut[i] = Double.parseDouble(scanner.nextLine());
        oMax = amax(sOut);
        for (int i = 0; i < tCount; i++) {
            tIn[i] = fromStrings(scanner.nextLine().split(sep));
            iMax = Math.max(iMax, amax(sIn[i]));
        }
        scanner.close();

        divAllWith(oMax, sOut);
        divAllWith(iMax, sIn);
        divAllWith(iMax, tIn);
    }
    static void divAllWith(double d, double[] a) {
        for(int i = 0; i < a.length; i++)
            a[i] /= d;
    }
    static void divAllWith(double s, double[][] a) {
        for (double[] anA : a) divAllWith(s, anA);
    }
    static double[] fromStrings(String[] strings) {
        double[] result = new double[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = Double.parseDouble(strings[i]);
        }
        return result;
    }
    static double amax(double[] a) {
        double max = a[0];
        for(int i = 1; i < a.length; i++)
            if(a[i] > max) max = a[i];
        return max;
    }

    static class Neuron {
        double[] w;
        double out;
        Neuron(double[] w) { this.w = w; }
    }
    static Neuron[] newLayer() {
        Neuron[] ns = new Neuron[fCount];
        for(int i = 0; i < fCount; i++)
            ns[i] = new Neuron(rand(fCount));
        return ns;
    }
    static double[] rand(int n) {
        double[] r = new double[n];
        for(int i = 0; i < n; i++)
            r[i] = rand.nextDouble();
        return r;
    }

    static Neuron[] inpL, hidL;
    static Neuron outN;
    static double r = 0.01;

    static void init() {
        rand = new Random();
        outN = new Neuron(rand(fCount));
        inpL = newLayer();
        hidL = newLayer();
    }
    static double forward(double[] in) {
        for(int i = 0; i < fCount; i++) inpL[i].out = in[i];
        for(int i = 0; i < fCount; i++) {
            hidL[i].out = 0;
            for(int j = 0; j < fCount; j++)
                hidL[i].out += inpL[j].out * hidL[i].w[j];
        }
        outN.out = 0;
        for(int i = 0; i < fCount; i++)
            outN.out += hidL[i].out * outN.w[i];
        return outN.out;
    }
    static void backward(double[] in, double a, double p) {
        double d = r * (p - a);
        for(int i = 0; i < fCount; i++) {
            for (int j = 0; j < fCount; j++)
                hidL[i].w[j] -= d * outN.w[i] * inpL[j].out;
            outN.w[i] -= d * hidL[i].out;
        }
    }
    static void train(double[] in, double o) { backward(in, o, forward(in)); }

    static void print(Object o) { System.out.print(o.toString()); }
    static void println() { System.out.println(); }
    static void println(double[][] a) {
        for(int i = 0; i < a.length; i++) {
            for(int j = 0; j < a[i].length; j++)
                print(a[i][j] + "\t");
            println();
        }
    }

}
