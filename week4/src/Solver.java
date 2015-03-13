import java.util.Comparator;


public class Solver {
    private final boolean solve;
    private final Node result;
    private final Comparator<Node> BY_HAMMING = new ByHamming();
    private final Comparator<Node> BY_MANHATTAN = new ByManhattan();
    
    private class ByHamming implements Comparator<Node> {
        public int compare(Node v, Node w) {
            return v.hpriority - w.hpriority;
        }
    }
    
    private class ByManhattan implements Comparator<Node> {
        public int compare(Node v, Node w) {
            return v.mpriority - w.mpriority;
        }
    }
    
//    private MinPQ<Node> hQueue = new MinPQ<Node>(BY_HAMMING);
//    private MinPQ<Node> mQueue = new MinPQ<Node>(BY_MANHATTAN);
    
    private class Node {
        private Board a;
        private int steps;
        private int hpriority;
        private int mpriority;
        private Node previous;
        
        public Node(Board b, int s, Node p) {
            a = b;
            steps = s;
            hpriority = a.hamming() + steps;
            mpriority = a.manhattan() + steps;
            previous = p;
        }
    }
    
    public Solver(Board initial) {
        boolean needSearch = true;
        if (initial == null)
            throw new NullPointerException("initial is null");
        boolean tmpSolve = false;
        Node neighNode;
        Node tmpNode = new Node(initial, 0, null);
        Node tmpTwin = new Node(initial.twin(), 0, null);
        MinPQ<Node> hQueue = new MinPQ<Node>(BY_MANHATTAN);
        MinPQ<Node> hTwinQueue = new MinPQ<Node>(BY_MANHATTAN);
        
//        hQueue.insert(tmpNode);
//        hTwinQueue.insert(tmpTwin);
        if (tmpNode.a.isGoal()) {
            tmpSolve = true;
            needSearch = false;
        }
        if (tmpTwin.a.isGoal()) {
            tmpSolve = false;
            needSearch = false;
        }
        if (needSearch) {
            while (true) {
                Queue<Board> neighQ = new Queue<Board>();
                for (Board i : tmpNode.a.neighbors())
                    neighQ.enqueue(i);
                while (!neighQ.isEmpty()) {
                    neighNode = new Node(neighQ.dequeue(), 
                            tmpNode.steps + 1, tmpNode);
                    if (tmpNode.previous == null)
                        hQueue.insert(neighNode);
                    else {
                        if (!neighNode.a.equals(tmpNode.previous.a))
                            hQueue.insert(neighNode);
                    }
                }
                tmpNode = hQueue.delMin();
                if (tmpNode.a.isGoal()) {
                    tmpSolve = true;
                    break;
                }
                Queue<Board> neighTwinQ = new Queue<Board>();
                for (Board i : tmpTwin.a.neighbors())
                    neighTwinQ.enqueue(i);
                while (!neighTwinQ.isEmpty()) {
                    neighNode = new Node(neighTwinQ.dequeue(), 
                            tmpTwin.steps + 1, tmpTwin);
                    if (tmpTwin.previous == null)
                        hTwinQueue.insert(neighNode);
                    else {
                        if (!neighNode.a.equals(tmpTwin.previous.a))
                            hTwinQueue.insert(neighNode);
                    }
                }
                tmpTwin = hTwinQueue.delMin();
                if (tmpTwin.a.isGoal()) {
                    break;
                }
            }
        }
        solve = tmpSolve;
        result = tmpNode;
    }
    
    public boolean isSolvable() {
        return solve;
    }
    
    public int moves() {
        if (!solve)
            return -1;
        return result.steps;
    }
    
    public Iterable<Board> solution() {
        if (!solve)
            return null;
        Stack<Board> bStack = new Stack<Board>();
        Node y = result;
        do {
            bStack.push(y.a);
            y = y.previous;
        } while (y != null);
        return bStack;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
