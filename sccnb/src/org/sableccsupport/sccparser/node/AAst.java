/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.sableccsupport.sccparser.node;

import java.util.*;
import org.sableccsupport.sccparser.analysis.*;

@SuppressWarnings("nls")
public final class AAst extends PAst
{
    private final LinkedList<PAstProd> _prods_ = new LinkedList<PAstProd>();

    public AAst()
    {
        // Constructor
    }

    public AAst(
        @SuppressWarnings("hiding") List<PAstProd> _prods_)
    {
        // Constructor
        setProds(_prods_);

    }

    @Override
    public Object clone()
    {
        return new AAst(
            cloneList(this._prods_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAst(this);
    }

    public LinkedList<PAstProd> getProds()
    {
        return this._prods_;
    }

    public void setProds(List<PAstProd> list)
    {
        this._prods_.clear();
        this._prods_.addAll(list);
        for(PAstProd e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._prods_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._prods_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        for(ListIterator<PAstProd> i = this._prods_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PAstProd) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        throw new RuntimeException("Not a child.");
    }
}
