import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] opened;
    private int size;
    private int top = 0;
    private int bottom;
    private WeightedQuickUnionUF qf;
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n){
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid Size");
        }
        size = n;
        bottom = size*size+1;
        qf = new WeightedQuickUnionUF(size*size+2);
        opened = new boolean[size][size];
    }
    
    // open site (row, col) if it is not open already
    public void open(int row, int col){
        if(isWithinRange(row,col)){
            opened[row-1][col-1] = true;
            int qfIndex = getQFIndex(row,col);
            
            if (row == 1) {
                qf.union(qfIndex, top);
            }
            if (row == size) {
                qf.union(qfIndex, bottom);
            }
            if (col > 1 && isOpen(row, col - 1)) {
                qf.union(qfIndex, getQFIndex(row, col - 1));
            }
            if (col < size && isOpen(row, col + 1)) {
                qf.union(qfIndex, getQFIndex(row, col + 1));
            }
            if (row > 1 && isOpen(row - 1, col)) {
                qf.union(qfIndex, getQFIndex(row - 1, col));
            }
            if (row < size && isOpen(row + 1, col)) {
                qf.union(qfIndex, getQFIndex(row + 1, col));
            }
        }
    }    
    
    // is site (row, col) open?
    public boolean isOpen(int row, int col){
        return opened[row-1][col-1];
    }  
    
    // is site (row, col) full?
    public boolean isFull(int row, int col){
        if(isWithinRange(row,col)){
            return qf.connected(top, getQFIndex(row , col));
        }else{
            return false;
        }
    }  
    // number of open sites
    public int numberOfOpenSites() {
        int number = 0;
        for(int i =0; i< size; i++){
            for(int j = 0; j< size; j++){
                if(opened[i][j]){
                    number ++;
                }
            }
        }
        return number;
    }      
    
// does the system percolate?
    public boolean percolates(){
        return qf.connected(top, bottom);
    }       
    
    private int getQFIndex(int row, int col){
        return size*(row-1)+col;
    }
    
    private boolean isWithinRange(int row, int col){
        if (0 < row && row <= size && 0 < col && col <= size) {
            return true;
        }
        
        throw new IndexOutOfBoundsException();
    }
    
    public static void main(String[] args){
    } // test client (optional)
}
