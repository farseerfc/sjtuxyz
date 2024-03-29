/* This file was generated by SableCC (http://www.sablecc.org/). */

package xyzlex.node;

import xyzlex.analysis.*;

@SuppressWarnings("nls")
public final class TLeftP extends Token
{
    public TLeftP()
    {
        super.setText("(");
    }

    public TLeftP(int line, int pos)
    {
        super.setText("(");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TLeftP(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTLeftP(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TLeftP text.");
    }
}
