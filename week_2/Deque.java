import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>
{

    private Node firstSentinel; // sentinel before first item
    private Node lastSentinel; // sentinel after last item
    private int n;

    private class Node
    {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Deque()
    {
        firstSentinel = new Node();
        lastSentinel = new Node();
        firstSentinel.next = lastSentinel;
        lastSentinel.prev = firstSentinel;
    }

    public boolean isEmpty()
    {
        return n == 0;
    }

    public int size()
    {
        return n;
    }

    public void addFirst(Item item)
    {
        if (item == null)
            throw new java.lang.IllegalArgumentException();
        else
        {
            Node first = firstSentinel.next;
            Node x = new Node();
            x.item = item;
            x.prev = firstSentinel;
            x.next = first;
            firstSentinel.next = x;
            first.prev = x;
            n++;
        }
    }

    public void addLast(Item item)
    {
        if (item == null)
            throw new java.lang.IllegalArgumentException();
        else
        {
            Node last = lastSentinel.prev;
            Node x = new Node();
            x.item = item;
            x.next = lastSentinel;
            x.prev = last;
            lastSentinel.prev = x;
            last.next = x;
            n++;
        }
    }

    public Item removeFirst()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException();
        }
        else
        {
            Node oldFirst = firstSentinel.next;
            Item value = oldFirst.item;
            Node newFirst = oldFirst.next;
            firstSentinel.next = newFirst;
            newFirst.prev = firstSentinel;
            n--;
            return value;
        }
    }

    public Item removeLast()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException();
        }
        else
        {
            Node oldLast = lastSentinel.prev;
            Item value = oldLast.item;
            Node newLast = oldLast.prev;
            lastSentinel.prev = newLast;
            newLast.next = lastSentinel;
            n--;
            return value;
        }
    }

    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {

        private Node current = firstSentinel.next;

        public boolean hasNext()
        {
            return current != lastSentinel;
        }

        public void remove()
        {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next()
        {
            if (current == lastSentinel)
                throw new java.util.NoSuchElementException();
            else
            {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }

    public static void main(String[] args)
    {

    }
}