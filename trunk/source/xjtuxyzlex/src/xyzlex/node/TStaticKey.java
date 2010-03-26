/* This file was generated by SableCC (http://www.sablecc.org/). */

package xyzlex.node;

import xyzlex.analysis.*;

@SuppressWarnings("nls")
public final class TStaticKey extends Token
{
    public TStaticKey()
    {
        super.setText("static");
    }

    public TStaticKey(int line, int pos)
    {
        super.setText("static");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TStaticKey(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTStaticKey(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TStaticKey text.");
    }
}