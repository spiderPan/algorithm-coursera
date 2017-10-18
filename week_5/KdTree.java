import edu.princeton.cs.algs4.*;

public class KdTree {
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;
    
    private class Node{
        private Node left;
        private Node right;
        private RectHV rect;
        private Point2D value;
        
        private boolean type;
        private int size;
        
        
        public Node(Point2D val, boolean type, RectHV rect){
            this.value = val;
            this.type = type;
            this.rect = rect;
            this.size = 1;
        }
        
        public int compare(double p1, double p2){
            if(p1 > p2){
                return 1;
            }else if(p1 < p2){
                return -1;
            }else{
                return 0;
            }
        }
        
        public int compareTo(Point2D p){
            if(this.type == HORIZONTAL){  
                int cmp = compare(this.value.x(),p.x());
                if(cmp!=0){
                    return cmp;
                }
                
                return compare(this.value.y(), p.y());
            }
            
            int cmp = compare(this.value.y(), p.y());
            if (cmp != 0) {
                return cmp;
            }
            return compare(this.value.x(), p.x());
            
        }
        
        public int compareTo(RectHV r){
            double x = this.value.x();
            double y = this.value.y();
            
            if(this.type == HORIZONTAL){  
                if (x > r.xmax()) {
                    return 1;
                }
                if (x < r.xmin()) {
                    return -1;
                }
            }else{
                if (y > r.ymax()) {
                    return 1;
                }
                if (y < r.ymin()) {
                    return -1;
                }
                
            }
            return 0;
        }
        
        public RectHV getRect(Point2D p){
            double xmin = this.rect.xmin();
            double xmax = this.rect.xmax();
            double ymin = this.rect.ymin();
            double ymax = this.rect.ymax();
            if (this.compareTo(p) > 0) {
                if (this.type == HORIZONTAL) {
                    xmax = this.value.x();
                } else {
                    ymax = this.value.y();
                }
            } else {
                if (this.type == HORIZONTAL) {
                    xmin = this.value.x();
                } else {
                    ymin = this.value.y();
                }
            }
            
            return new RectHV(xmin, ymin, xmax, ymax);
        }
        
    }
    
    private Node root = null;
    private Point2D minPoint = null;
    private double minDist = 0.0;
    
    
    public KdTree(){                              
// construct an empty set of points 
    }
    public boolean isEmpty(){                      
// is the set empty? 
        return root == null;
    }
    public int size(){                        
        // number of points in the set
        return getSize(root);
    }
    private int getSize(Node node){
        if(node == null){
            return 0;
        }
        
        return node.size;
    }
    public void insert(Point2D p){              
// add the point to the set (if it is not already in the set)
        if(p== null){
            throw new java.lang.IllegalArgumentException();
        }
        root = insertNode(root,p,HORIZONTAL,null);
    }
    
    private Node insertNode(Node node, Point2D p, boolean type, Node parent){
        if(node == null){
            RectHV r;
            
            if(parent == null){
                r = new RectHV(0.0, 0.0, 1.0, 1.0);
            }else{
                r = parent.getRect(p);
            }
            
            return new Node(p, type, r);
        }
        
        if(node.compareTo(p)>0){
            //go left
            node.left = insertNode(node.left, p, !type, node);
        }else if(node.compareTo(p) <0){
            //go right
            node.right = insertNode(node.right, p, !type, node);
        }else{
            node.value = p;
        }
        
        node.size = 1 + getSize(node.left) + getSize(node.right);
        
        return node;
    }
    public boolean contains(Point2D p){            
// does the set contain point p? 
        if(p== null){
            throw new java.lang.IllegalArgumentException();
        }
        
        return searchNode(root, p) != null;
    }
    
    private Node searchNode(Node node, Point2D p){
        if(node == null){
            return null;
        }
        
        if(node.compareTo(p)>0){
            return searchNode(node.left,p);
        }else if (node.compareTo(p)<0){
            return searchNode(node.right,p);
        }else{
            return node;
        }
    }
    public void draw(){                         
// draw all points to standard draw 
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(0, 0, 1, 0);
        StdDraw.line(1, 0, 1, 1);
        StdDraw.line(1, 1, 0, 1);
        StdDraw.line(0, 1, 0, 0);
        
        drawNode(root, null);
    }
    
    private void drawNode(Node node, Node parent) {
        if (node == null) {
            return;
        }
        
        StdDraw.setPenColor(StdDraw.BLACK);
        node.value.draw();
        
        double x = node.value.x();
        double y = node.value.y();
        
        if (node.type == HORIZONTAL) {
            StdDraw.setPenColor(StdDraw.RED);
            
            StdDraw.line(x, node.rect.ymin(), x, node.rect.ymax());
            
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            
            StdDraw.line(node.rect.xmin(), y, node.rect.xmax(), y);
        }
        
        drawNode(node.left, node);
        drawNode(node.right, node);
    }
    public Iterable<Point2D> range(RectHV rect){             
// all points that are inside the rectangle (or on the boundary) 
        if(rect== null){
            throw new java.lang.IllegalArgumentException();
        }
        
        Stack<Point2D> stack = new Stack<Point2D>();
        
        rangeNode(root, rect, stack);
        
        return stack;
    }
    
    private void rangeNode(Node node, RectHV rect, Stack<Point2D> stack){
        if(node == null){
            return;
        }
        
        if(rect.contains(node.value)){
            stack.push(node.value);
        }
        
        if(node.compareTo(rect)>0){
            rangeNode(node.left, rect, stack);
        }else if(node.compareTo(rect)<0){
            rangeNode(node.right, rect, stack);
        }else{
            rangeNode(node.left,rect,stack);
            rangeNode(node.right,rect,stack);
        }
        
    }
    
    public Point2D nearest(Point2D p){             
// a nearest neighbor in the set to point p; null if the set is empty 
        if(p== null){
            throw new java.lang.IllegalArgumentException();
        }
        
        if(isEmpty()){
            return null;
        }
        
        
        
        minPoint = root.value;
        minDist = minPoint.distanceSquaredTo(p);
        
        searchNearest(root,p);
        return minPoint;
    }
    
    private void searchNearest(Node node, Point2D p){
        double dist = node.value.distanceSquaredTo(p);
        
        if(minDist > dist){
            minPoint = node.value;
            minDist = dist;
        }
        
        if(node.left != null && node.right != null){
            double leftDist = node.left.rect.distanceSquaredTo(p);
            double rightDist = node.right.rect.distanceSquaredTo(p);
            
            if(leftDist<rightDist){
                searchNearest(node.left,p);
                
                if(rightDist<minDist){
                    searchNearest(node.right, p);
                }
            }else{
                searchNearest(node.right, p);
                
                if(leftDist<minDist){
                    searchNearest(node.left, p);
                }
            }
            
            return;
        }
        
        if (node.left != null) {
            if (node.left.rect.distanceSquaredTo(p) < minDist) {
                searchNearest(node.left, p);
            }
        }
        
        if (node.right != null) {
            if (node.right.rect.distanceSquaredTo(p) < minDist) {
                searchNearest(node.right, p);
            }
        }
        
        return;
    }
    
    public static void main(String[] args){                  
// unit testing of the methods (optional) 
    }
}