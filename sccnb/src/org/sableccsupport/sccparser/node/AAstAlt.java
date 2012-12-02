/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.sableccsupport.sccparser.node;

import java.util.*;
import org.sableccsupport.sccparser.analysis.*;

@SuppressWarnings("nls")
public final class AAstAlt extends PAstAlt
{
    private TId _altName_;
    private final LinkedList<PElem> _elems_ = new LinkedList<PElem>();

    public AAstAlt()
    {
        // Constructor
    }

    public AAstAlt(
        @SuppressWarnings("hiding") TId _altName_,
        @SuppressWarnings("hiding") List<?> _elems_)
    {
        // Constructor
        setAltName(_altName_);

        setElems(_elems_);

    }

    @Override
    public Object clone()
    {
        return new AAstAlt(
            cloneNode(this._altName_),
            cloneList(this._elems_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAstAlt(this);
    }

    public TId getAltName()
    {
        return this._altName_;
    }

    public void setAltName(TId node)
    {
        if(this._altName_ != null)
        {
            this._altName_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._altName_ = node;
    }

    public LinkedList<PElem> getElems()
    {
        return this._elems_;
    }

    public void setElems(List<?> list)
    {
        for(PElem e : this._elems_)
        {
            e.parent(null);
        }
        this._elems_.clear();

        for(Object obj_e : list)
        {
            PElem e = (PElem) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._elems_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._altName_)
            + toString(this._elems_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._altName_ == child)
        {
            this._altName_ = null;
            return;
        }

        if(this._elems_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._altName_ == oldChild)
        {
            setAltName((TId) newChild);
            return;
        }

        for(ListIterator<PElem> i = this._elems_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PElem) newChild);
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
