/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.sableccsupport.sccparser.node;

import org.sableccsupport.sccparser.analysis.*;

@SuppressWarnings("nls")
public final class TProductions extends Token
{
    public TProductions()
    {
        super.setText("Productions");
    }

    public TProductions(int line, int pos)
    {
        super.setText("Productions");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TProductions(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTProductions(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TProductions text.");
    }
}
