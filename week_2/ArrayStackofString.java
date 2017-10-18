public class ArrayStackofString {
    private String[] s;
    private int N = 0;
    
    public arrayStackofString(int capacity){
        s = new String[capcity];
    }
    
    public boolean isEmpty(){
        return N == 0;
    }
    
    public void push(String item){
        s[N++] = item;
    }
   
    public String pop(){
        return s[--N];
    }
}
