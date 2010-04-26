/* This file was generated by SableCC (http://www.sablecc.org/). */

package xyzlex.node;

import xyzlex.analysis.*;

@SuppressWarnings("nls")
public final class ALeftValueExp extends PExp
{
    private PLeftValue _leftValue_;

    public ALeftValueExp()
    {
        // Constructor
    }

    public ALeftValueExp(
        @SuppressWarnings("hiding") PLeftValue _leftValue_)
    {
        // Constructor
        setLeftValue(_leftValue_);

    }

    @Override
    public Object clone()
    {
        return new ALeftValueExp(
            cloneNode(this._leftValue_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseALeftValueExp(this);
    }

    public PLeftValue getLeftValue()
    {
        return this._leftValue_;
    }

    public void setLeftValue(PLeftValue node)
    {
        if(this._leftValue_ != null)
        {
            this._leftValue_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._leftValue_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._leftValue_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._leftValue_ == child)
        {
            this._leftValue_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._leftValue_ == oldChild)
        {
            setLeftValue((PLeftValue) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}