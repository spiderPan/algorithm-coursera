import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private int experimentsCount;
    private int size;
    private Percolation pr;
    private double[] fractions;
    
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n<=0 || trials<=0){
            throw new IllegalArgumentException("Invalid Size or Trial");
        }
        size = n;
        experimentsCount = trials;
        fractions = new double[trials];
        
        for(int expNum = 0; expNum<experimentsCount;expNum++){
            pr = new Percolation(size);
            int openedSites = 0;
            while(!pr.percolates()){
                int i = StdRandom.uniform(1, size + 1);
                int j = StdRandom.uniform(1, size + 1);
                if (!pr.isOpen(i, j)) {
                    pr.open(i, j);
                    openedSites++;
                }
            }
            double fraction = (double) openedSites/ (size*size);
            fractions[expNum] = fraction;
        }
    }   
    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(fractions);
    }                         
    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(fractions);
    }                 
    // low  endpoint of 95% confidence interval 
    public double confidenceLo(){
        return mean() - ((1.96 * stddev()) / Math.sqrt(experimentsCount));
    }            
    // high endpoint of 95% confidence interval 
    public double confidenceHi(){
        return mean() + ((1.96 * stddev()) / Math.sqrt(experimentsCount));
    }                  
    
    public static void main(String[] args){
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        PercolationStats ps = new PercolationStats(N,T);
        
        String confidence = "["+ ps.confidenceLo()+", "+ ps.confidenceHi()+"]";
        StdOut.println("mean                    = "+ps.mean());
        StdOut.println("stddev                  = "+ps.stddev());
        StdOut.println("95% confidence interval = "+confidence);
    }
}
