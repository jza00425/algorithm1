
public class Board {
    private final int[][] data;
    private final int N;
//    private final Queue<Board> q;
    
    public Board(int[][] blocks) {
        N = blocks.length;
        data = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                data[i][j] = blocks[i][j];
            }
    }
    
    public int dimension() {
        return N;
    }
    
    private int twoToOne(int i, int j) {
        if ((i == N - 1) && (j == N - 1))
            return 0;
        return i * N + j + 1;
    }
    
    private int iDist(int value, int i) {
        return Math.abs((value - 1) / N - i); 
    }
    
    private int jDist(int value, int j) {
        return Math.abs((value - 1) % N - j); 
    }
    
    public int hamming() {
        int ham = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (data[i][j] != twoToOne(i, j) && data[i][j] != 0)
                    ham++;
            }
        return ham;
    }
    
    public int manhattan() {
        int man = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (data[i][j] != 0)
                    man = man + iDist(data[i][j], i) + jDist(data[i][j], j);
            }    
        return man;
    }
    
    public boolean isGoal() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (data[i][j] != twoToOne(i, j))
                    return false;
            }
        return true;   
    }
    
    public Board twin() {
        int mid;
        int[][] tmp = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                tmp[i][j] = data[i][j];
        if (tmp[0][0] != 0 && tmp[0][1] != 0) {
            mid = tmp[0][0];
            tmp[0][0] = tmp[0][1];
            tmp[0][1] = mid;
        } else {
            mid = tmp[1][0];
            tmp[1][0] = tmp[1][1];
            tmp[1][1] = mid;
        }
        return new Board(tmp);
    }
    
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (this.N != that.N) return false;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (this.data[i][j] != that.data[i][j])
                    return false;
            }
        return true;
    }
    
    public Iterable<Board> neighbors() {
        int blankI = 0, blankJ = 0;
        int mid;
        Queue<Board> q = new Queue<Board>();
        int[][] tmp = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tmp[i][j] = data[i][j];
                if (data[i][j] == 0) {
                    blankI = i;
                    blankJ = j;
                }
            }
        }
        if (blankI - 1 >= 0) {
            mid = tmp[blankI - 1][blankJ];
            tmp[blankI - 1][blankJ] = tmp[blankI][blankJ];
            tmp[blankI][blankJ] = mid;
            q.enqueue(new Board(tmp));
            mid = tmp[blankI - 1][blankJ];
            tmp[blankI - 1][blankJ] = tmp[blankI][blankJ];
            tmp[blankI][blankJ] = mid;  
        }
        if (blankI + 1 < N) {
            mid = tmp[blankI + 1][blankJ];
            tmp[blankI + 1][blankJ] = tmp[blankI][blankJ];
            tmp[blankI][blankJ] = mid;
            q.enqueue(new Board(tmp));
            mid = tmp[blankI + 1][blankJ];
            tmp[blankI + 1][blankJ] = tmp[blankI][blankJ];
            tmp[blankI][blankJ] = mid;  
        }
        if (blankJ - 1 >= 0) {
            mid = tmp[blankI][blankJ - 1];
            tmp[blankI][blankJ - 1] = tmp[blankI][blankJ];
            tmp[blankI][blankJ] = mid;
            q.enqueue(new Board(tmp));
            mid = tmp[blankI][blankJ - 1];
            tmp[blankI][blankJ - 1] = tmp[blankI][blankJ];
            tmp[blankI][blankJ] = mid;  
        }
        if (blankJ + 1 < N) {
            mid = tmp[blankI][blankJ + 1];
            tmp[blankI][blankJ + 1] = tmp[blankI][blankJ];
            tmp[blankI][blankJ] = mid;
            q.enqueue(new Board(tmp));
            mid = tmp[blankI][blankJ + 1];
            tmp[blankI][blankJ + 1] = tmp[blankI][blankJ];
            tmp[blankI][blankJ] = mid;  
        }
        return q;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", data[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
//        Board twin1 = initial.twin();
        StdOut.println(initial.toString());
//        StdOut.println(twin1.toString());
        Queue<Board> tmp = (Queue<Board>) initial.neighbors();
        while (true) {
            if (tmp.isEmpty())
                break;
            StdOut.println(tmp.dequeue().toString());
        }
    }

}
