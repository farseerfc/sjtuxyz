/* This file was generated by SableCC (http://www.sablecc.org/). */

package xyzlex.node;

import xyzlex.analysis.*;

@SuppressWarnings("nls")
public final class TGreaterOpr extends Token
{
    public TGreaterOpr()
    {
        super.setText(">");
    }

    public TGreaterOpr(int line, int pos)
    {
        super.setText(">");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TGreaterOpr(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTGreaterOpr(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TGreaterOpr text.");
    }
}
