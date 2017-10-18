import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int[][] newBoard;
    private final int n;
    
    public Board(int[][] blocks){
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        n = blocks.length;
        
        newBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            newBoard[i] = Arrays.copyOf(blocks[i], blocks[i].length);
        }
    }           
    
    public int dimension(){
        // board dimension n
        return n;
    }                 
    public int hamming(){
        // number of blocks out of place
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (newBoard[i][j] != goalValueAt(i, j) && !isEnd(i, j)) {
                    sum++;
                }
            }
        }
        return sum;
    }
    
    private int[][] createGoalBoard(){
        int[][] array = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = goalValueAt(i,j);
            }
        }
        
        return array;
    }
    
    private boolean isEnd(int i, int j) {
        return i == n - 1 && j == n - 1;
    }
    
    private int goalValueAt(int x, int y){
        if(isEnd(x,y)){
            return 0;
        }
        
        return 1 + x*n + y;
        
    }
    public int manhattan(){
        // sum of Manhattan distances between blocks and goal
        int dis = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = newBoard[i][j];
                if (value != goalValueAt(i, j) && !isEnd(i, j) && value != 0) {
                    int initialX = ( value -1 )/n;
                    int initialY = value - 1 - initialX*n ;
                    
                    dis += Math.abs(initialX -i) + Math.abs(initialY - j);
                }
            }
        }
        return dis;
    }                 
    public boolean isGoal(){
        // is this board the goal board?
        return boardEqual(this.newBoard,createGoalBoard());
    }                
    public Board twin(){
        // a board that is obtained by exchanging any pair of blocks
        
        Board board = new Board(newBoard);
        
        for(int i = 0; i< n; i++){
            for (int j = 0; j< n-1; j++){
                if(newBoard[i][j] != 0 && newBoard[i][j+1]!=0){
                    board.swap(i,j,i,j+1);
                    return board;
                }
            }
        }
        
        return null;
    }
    
    private boolean swap(int i, int j, int x, int y){
        if(x<0 || x>=n || y<0|| y>=n){
            return false;
        }
        
        int temp = newBoard[i][j];
        newBoard[i][j] = newBoard[x][y];
        newBoard[x][y] = temp;
        
        return true;
    }
    
    
    public boolean equals(Object y){
        // does this board equal y?
        if(y==this){
            return true;
        }
        if(y == null){
            return false;
        }
        if(y.getClass() != this.getClass()){
            return false;
        }
        
        Board that = (Board) y;
        return this.n == that.n && boardEqual(this.newBoard, that.newBoard);
        
    }
    
    private boolean boardEqual(int[][] first, int[][] second){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (first[i][j] != second[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    public Iterable<Board> neighbors(){
        // all neighboring boards
        int i0 = 0, j0 = 0;
        find_empty_block:
        
        for(int i = 0; i<n;i++){
            for(int j = 0; j<n; j++){
                if(newBoard[i][j] == 0){
                    i0 = i;
                    j0 = j;
                    
                    break find_empty_block;
                }
            }
        }
        
        
        List<Board> neighbors = new ArrayList<Board>();
        
        Board board = new Board(newBoard);

        // Swap with the around side
        // Swap up side
        if (board.swap(i0, j0, i0 - 1, j0)) {
            neighbors.add(board);
        }

        // Swap down side
        board = new Board(newBoard);
        if (board.swap(i0, j0, i0 + 1, j0)) {
            neighbors.add(board);
        }

        // Swap left side
        board = new Board(newBoard);
        if (board.swap(i0, j0, i0, j0 - 1)) {
            neighbors.add(board);
        }

        // Swap right side
        board = new Board(newBoard);
        if (board.swap(i0, j0, i0, j0 + 1)) {
            neighbors.add(board);
        }

        return neighbors;
    }     
    public String toString(){
        // string representation of this board (in the output format specified below)
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stringBuilder.append(String.format("%2d", newBoard[i][j]));
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }               
    
    public static void main(String[] args){
        // unit tests (not graded)
    } 
}