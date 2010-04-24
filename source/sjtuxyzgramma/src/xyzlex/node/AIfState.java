/* This file was generated by SableCC (http://www.sablecc.org/). */

package xyzlex.node;

import xyzlex.analysis.*;

@SuppressWarnings("nls")
public final class AIfState extends PState
{
    private PExp _cond_;
    private PState _then_;
    private PState _else_;

    public AIfState()
    {
        // Constructor
    }

    public AIfState(
        @SuppressWarnings("hiding") PExp _cond_,
        @SuppressWarnings("hiding") PState _then_,
        @SuppressWarnings("hiding") PState _else_)
    {
        // Constructor
        setCond(_cond_);

        setThen(_then_);

        setElse(_else_);

    }

    @Override
    public Object clone()
    {
        return new AIfState(
            cloneNode(this._cond_),
            cloneNode(this._then_),
            cloneNode(this._else_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAIfState(this);
    }

    public PExp getCond()
    {
        return this._cond_;
    }

    public void setCond(PExp node)
    {
        if(this._cond_ != null)
        {
            this._cond_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._cond_ = node;
    }

    public PState getThen()
    {
        return this._then_;
    }

    public void setThen(PState node)
    {
        if(this._then_ != null)
        {
            this._then_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._then_ = node;
    }

    public PState getElse()
    {
        return this._else_;
    }

    public void setElse(PState node)
    {
        if(this._else_ != null)
        {
            this._else_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._else_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._cond_)
            + toString(this._then_)
            + toString(this._else_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._cond_ == child)
        {
            this._cond_ = null;
            return;
        }

        if(this._then_ == child)
        {
            this._then_ = null;
            return;
        }

        if(this._else_ == child)
        {
            this._else_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._cond_ == oldChild)
        {
            setCond((PExp) newChild);
            return;
        }

        if(this._then_ == oldChild)
        {
            setThen((PState) newChild);
            return;
        }

        if(this._else_ == oldChild)
        {
            setElse((PState) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
