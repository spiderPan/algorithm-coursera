import java.util.*;
import edu.princeton.cs.algs4.*;

public class FastCollinearPoints {
    private List<LineSegment> lineSegementsList = new ArrayList<LineSegment>();
    private int lineSegmentNum;
    
    public FastCollinearPoints(Point[] points){
        if(points == null){
            throw new java.lang.IllegalArgumentException();
        }
        
        Point[] slopePoints = new Point[points.length];
        
        for(int k = 0; k<points.length;k++){
            if(points[k]==null
                   ||(k>0 && slopePoints[k-1].slopeTo(points[k]) ==  Double.NEGATIVE_INFINITY) ){
                throw new java.lang.IllegalArgumentException();
            }
            slopePoints[k] = points[k];
        }
        
        Arrays.sort(points);
        
        for(int i = 0; i<slopePoints.length;i++){
            Arrays.sort(slopePoints);
            Arrays.sort(slopePoints, points[i].slopeOrder());
            
            for(int j = 1; j<slopePoints.length-2;j++){
                if ((points[i].slopeTo(slopePoints[j]) == points[i].slopeTo(slopePoints[j + 1]))
                        && (points[i].slopeTo(slopePoints[j]) == points[i].slopeTo(slopePoints[j + 2]))
                        && points[i].compareTo(slopePoints[j]) < 1
                        && slopePoints[j].compareTo(slopePoints[j + 1]) < 1
                        && slopePoints[j + 1].compareTo(slopePoints[j + 2]) < 1)
                    lineSegementsList.add(new LineSegment(points[i], slopePoints[j + 2]));
                lineSegmentNum++;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}