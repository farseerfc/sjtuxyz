/* This file was generated by SableCC (http://www.sablecc.org/). */

package xyzlex.node;

import xyzlex.analysis.*;

@SuppressWarnings("nls")
public final class TAndOpr extends Token
{
    public TAndOpr()
    {
        super.setText("&&");
    }

    public TAndOpr(int line, int pos)
    {
        super.setText("&&");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TAndOpr(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTAndOpr(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TAndOpr text.");
    }
}
