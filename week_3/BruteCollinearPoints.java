import java.util.*;
import edu.princeton.cs.algs4.*;

public class BruteCollinearPoints {
    private List<LineSegment> lineSegementsList = new ArrayList<LineSegment>();
    private int lineSegmentNum;
    
    public BruteCollinearPoints(Point[] points){    
        // finds all line segments containing 4 points
        //Corner cases. Throw a java.lang.IllegalArgumentException if the argument to the constructor is null, 
        //if any point in the array is null, or if the argument to the constructor contains a repeated point.
        if(points == null){
            throw new java.lang.IllegalArgumentException();
        }
        Arrays.sort(points);
        
        for(int i=0; i<points.length;i++){
            if(points[i]==null){
                throw new java.lang.IllegalArgumentException();
            }
            for(int j=i+1; j<points.length;j++){
                if(points[i].slopeTo(points[j]) ==  Double.NEGATIVE_INFINITY){
                    throw new java.lang.IllegalArgumentException();
                }
                for(int k=j+1; k<points.length;k++){
                    for(int l=k+1; l<points.length;l++){
                        if(points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                               &&points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])
                               &&points[i].compareTo(points[j]) < 1 
                               && points[j].compareTo(points[k]) < 1 
                               && points[k].compareTo(points[l]) < 1){
                            lineSegementsList.add(new LineSegment(points[i],points[l]));
                            lineSegmentNum++;
                        }
                    } 
                } 
            } 
        }
    }
    public int numberOfSegments(){  
        // the number of line segments
        return lineSegmentNum;
    }
    public LineSegment[] segments(){                
        // the line segments,The method segments() should include each line segment containing 4 points exactly once. 
        //If 4 points appear on a line segment in the order p?q?r?s, then you should include either the line segment 
        //p?s or s?p (but not both) and you should not include subsegments such as p?r or q?r. For simplicity, 
        //we will not supply any input to BruteCollinearPoints that has 5 or more collinear points.
        return lineSegementsList.toArray(new LineSegment[lineSegementsList.size()]);
    }
    
    public static void main(String[] args) {
        
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}