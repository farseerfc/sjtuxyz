/* This file was generated by SableCC (http://www.sablecc.org/). */

package xyzlex.node;

import xyzlex.analysis.*;

@SuppressWarnings("nls")
public final class TPostKey extends Token
{
    public TPostKey()
    {
        super.setText("post");
    }

    public TPostKey(int line, int pos)
    {
        super.setText("post");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TPostKey(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTPostKey(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TPostKey text.");
    }
}