/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.sableccsupport.scclexer.node;

import java.util.*;
import org.sableccsupport.scclexer.analysis.*;

@SuppressWarnings("nls")
public final class AAstProd extends PAstProd
{
    private TId _id_;
    private final LinkedList<PAstAlt> _alts_ = new LinkedList<PAstAlt>();

    public AAstProd()
    {
        // Constructor
    }

    public AAstProd(
        @SuppressWarnings("hiding") TId _id_,
        @SuppressWarnings("hiding") List<?> _alts_)
    {
        // Constructor
        setId(_id_);

        setAlts(_alts_);

    }

    @Override
    public Object clone()
    {
        return new AAstProd(
            cloneNode(this._id_),
            cloneList(this._alts_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAstProd(this);
    }

    public TId getId()
    {
        return this._id_;
    }

    public void setId(TId node)
    {
        if(this._id_ != null)
        {
            this._id_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._id_ = node;
    }

    public LinkedList<PAstAlt> getAlts()
    {
        return this._alts_;
    }

    public void setAlts(List<?> list)
    {
        for(PAstAlt e : this._alts_)
        {
            e.parent(null);
        }
        this._alts_.clear();

        for(Object obj_e : list)
        {
            PAstAlt e = (PAstAlt) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._alts_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._id_)
            + toString(this._alts_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._id_ == child)
        {
            this._id_ = null;
            return;
        }

        if(this._alts_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._id_ == oldChild)
        {
            setId((TId) newChild);
            return;
        }

        for(ListIterator<PAstAlt> i = this._alts_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PAstAlt) newChild);
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
