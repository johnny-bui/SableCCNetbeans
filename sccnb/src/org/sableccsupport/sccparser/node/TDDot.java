/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.sableccsupport.sccparser.node;

import org.sableccsupport.sccparser.analysis.*;

@SuppressWarnings("nls")
public final class TDDot extends Token
{
    public TDDot()
    {
        super.setText("..");
    }

    public TDDot(int line, int pos)
    {
        super.setText("..");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TDDot(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTDDot(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TDDot text.");
    }
}
