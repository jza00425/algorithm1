import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
public class Fast {
    
    

    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
//        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        String s;
        Point[] set = new Point[N];
        Point[] aux = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            set[i] = new Point(x, y);
            set[i].draw();
        }
//        StdDraw.show(0);
        Arrays.sort(set);
        for (int i = 0; i < N; i++)
            aux[i] = set[i];
        for (int i = 0; i < N; i++) {
            Arrays.sort(aux, set[i].SLOPE_ORDER);
            s = set[i].toString();
            int position = i;
            int cnt = 1;
            List<Integer> result = new ArrayList<Integer>();
            for (int j = 1; j < N - 1; j++) {
                if (aux[j].slopeTo(aux[0]) == aux[j + 1].slopeTo(aux[0])) {
                    if (cnt == 1) {
                        result.add(Arrays.asList(set).indexOf(aux[j]));
                        result.add(Arrays.asList(set).indexOf(aux[j + 1]));
                    } else {
                        result.add(Arrays.asList(set).indexOf(aux[j + 1]));
                    }
                    cnt++;
                    if (j == N - 2 && cnt >= 3) {
                        Collections.sort(result);
                        if (position < result.get(0)) {
                            for (int l : result)
                                s = s + " -> " + set[l].toString(); 
                            StdOut.println(s);
                            aux[0].drawTo(set[result.get(result.size() - 1)]);
                        }
                    }
                } else {
                    if (cnt >= 3) {
                        Collections.sort(result);
                        if (position < result.get(0)) {
                            for (int l : result)
                                s = s + " -> " + set[l].toString(); 
                            StdOut.println(s);
                            aux[0].drawTo(set[result.get(result.size() - 1)]);
                        }
                    }
                    cnt = 1;
                    result.clear();
                    s = set[i].toString();
                }
            }
        }
    }

}
