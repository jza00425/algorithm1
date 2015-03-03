import java.util.Arrays;

public class Brute {

    public static void main(String[] args) {
     // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        String s;
        Point[] set = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            set[i] = new Point(x, y);
            set[i].draw();
        }
        Arrays.sort(set);
        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++) {
                if (set[i].slopeTo(set[j]) == Double.NEGATIVE_INFINITY)
                    continue;
                for (int k = j + 1; k < N; k++) {
                    if ((set[i].slopeTo(set[j]) != set[i].slopeTo(set[k])) 
                        || (set[j].slopeTo(set[k]) == Double.NEGATIVE_INFINITY))
                        continue;
                    for (int l = k + 1; l < N; l++) {
                        if ((set[i].slopeTo(set[l]) != set[i].slopeTo(set[k])) 
                            || (set[k].slopeTo(set[l]) == Double.NEGATIVE_INFINITY))
                            continue;
                        else {
                            s = "";
                            s = set[i].toString() + " -> " + set[j].toString() 
                                    + " -> " + set[k].toString() + " -> " 
                                    + set[l].toString();
                            StdOut.println(s);
                            set[i].drawTo(set[l]);
                        }
                    }
                }
            }
//        StdDraw.show(0);
    }

}
