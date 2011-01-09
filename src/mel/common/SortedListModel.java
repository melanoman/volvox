package mel.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import javax.swing.AbstractListModel;

public class SortedListModel<T> extends AbstractListModel
{
    private static final long serialVersionUID = 1L;
    private final TreeSet<T> model = new TreeSet<T>();

    public int getSize() { return model.size(); }
    public Object firstElement() { return model.first(); }
    public Iterator<T> iterator() { return model.iterator(); }
    public Object lastElement() { return model.last(); }
    public boolean contains(Object element) { return model.contains(element); }
    
    public T getElementAt(int index) 
    {
        if(index < 0) throw new IndexOutOfBoundsException();
        int i = -1;
        for(T t:model) 
        {
            i++;
            if(i == index) return t;
        }
        throw new IndexOutOfBoundsException();
    }
    
    public void add(T element) 
    {
        if (model.add(element)) 
        {
            fireContentsChanged(this, 0, getSize());  
        }    
    }

    public void addAll(T elements[]) 
    {
        Collection<T> c = Arrays.asList(elements);
        model.addAll(c);
        fireContentsChanged(this, 0, getSize());
    }

    public void clear() 
    {
        model.clear();
        fireContentsChanged(this, 0, 0);
    }
    
    public boolean removeElement(Object element) 
    {
        if (model.remove(element))
        {
            fireContentsChanged(this, 0, getSize());
            return true;
        }
        return false;
    }
}
