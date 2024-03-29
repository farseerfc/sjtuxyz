/* This file was generated by SableCC (http://www.sablecc.org/). */

package xyzlex.node;

import xyzlex.analysis.*;

@SuppressWarnings("nls")
public final class TIfKey extends Token
{
    public TIfKey()
    {
        super.setText("if");
    }

    public TIfKey(int line, int pos)
    {
        super.setText("if");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TIfKey(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTIfKey(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TIfKey text.");
    }
}
