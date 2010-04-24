/* This file was generated by SableCC (http://www.sablecc.org/). */

package xyzlex.node;

import xyzlex.analysis.*;

@SuppressWarnings("nls")
public final class ARealLtExp extends PExp
{
    private TRealLt _realLt_;

    public ARealLtExp()
    {
        // Constructor
    }

    public ARealLtExp(
        @SuppressWarnings("hiding") TRealLt _realLt_)
    {
        // Constructor
        setRealLt(_realLt_);

    }

    @Override
    public Object clone()
    {
        return new ARealLtExp(
            cloneNode(this._realLt_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseARealLtExp(this);
    }

    public TRealLt getRealLt()
    {
        return this._realLt_;
    }

    public void setRealLt(TRealLt node)
    {
        if(this._realLt_ != null)
        {
            this._realLt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._realLt_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._realLt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._realLt_ == child)
        {
            this._realLt_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._realLt_ == oldChild)
        {
            setRealLt((TRealLt) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
