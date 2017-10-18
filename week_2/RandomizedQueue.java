import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] array;
    private int n = 0;
    
    public RandomizedQueue()
    {
        array = (Item[]) new Object[1];
    }
    
    public boolean isEmpty()
    {
        return n == 0;
    }
    
    public int size()
    {
        return n;
    }
    
    public void enqueue(Item item)
    {
        if (item == null)
            throw new java.lang.IllegalArgumentException();
        else
        {
            if (n == array.length)
                resize(2 * array.length);
            array[n++] = item;
        }
    }
    
    public Item dequeue()
    {
        if (n == 0)
        {
            throw new java.util.NoSuchElementException();
        }
        else
        {
            // Selecting an index at random, and then swapping it with the last index
            int randomIndex = StdRandom.uniform(0, n);
            Item temp = array[n - 1];
            array[n - 1] = array[randomIndex];
            array[randomIndex] = temp;
            
            Item item = array[--n];
            array[n] = null;
            if (n > 0 && n == array.length / 4)
                resize(array.length / 2);
            return item;
        }
    }
    
    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
        {
            copy[i] = array[i];
        }
        array = copy;
    }
    
    public Item sample()
    {
        if (n == 0)
            throw new java.util.NoSuchElementException();
        else
        {
            // Selecting an index at random, and then swapping it with the last index
            int randomIndex = StdRandom.uniform(0, n);
            
            return array[randomIndex];
        }
    }
    
    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private Item[] arrayCopy = (Item[]) new Object[n];
        private int i = n;
        private int randomIndex;
        
        public RandomizedQueueIterator()
        {
            for (int j = 0; j < n; j++)
            {
                arrayCopy[j] = array[j];
            }
        }
        
        
        public boolean hasNext()
        {
            return i > 0;
        }
        
        public void remove()
        {
            throw new java.lang.UnsupportedOperationException();
        }
        
        public Item next()
        {
            if (i == 0)
                throw new java.util.NoSuchElementException();
            else
            {
                randomIndex = StdRandom.uniform(0, i);
                Item temp = arrayCopy[i - 1];
                arrayCopy[i - 1] = arrayCopy[randomIndex];
                arrayCopy[randomIndex] = temp;
                return arrayCopy[--i];
            }
        }
        
    }
    
    public static void main(String[] args)
    {
        
    }
}