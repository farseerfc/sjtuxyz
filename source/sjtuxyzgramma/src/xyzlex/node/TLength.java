/* This file was generated by SableCC (http://www.sablecc.org/). */

package xyzlex.node;

import xyzlex.analysis.*;

@SuppressWarnings("nls")
public final class TLength extends Token
{
    public TLength()
    {
        super.setText("length");
    }

    public TLength(int line, int pos)
    {
        super.setText("length");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TLength(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTLength(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TLength text.");
    }
}
