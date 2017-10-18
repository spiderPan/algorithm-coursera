public class ResizingArrayStackofString {
    private String[] s;
    private int N = 0;
    private int size;
    
    public arrayStackofString(int capacity){
        s = new String[1];
    }
    
    public boolean isEmpty(){
        return N == 0;
    }
    
    public void push(String item){
        if(N == s.length){
            resize(2*s.length);
        }
        s[N++] = item;
    }
    
    public String pop(){
        String item = s[--N];
        s[N] =null;
        if(N == s.length/4 && N>0){
            resize(s.length/2);
        }
        return item;
    }
    
    public resize(int capacity){
        copy = new String[capacity];
        for(int i = 0; i<N; i++){
            copy[i] = s[i];
        }
        
        s = copy;
        
    }
}
