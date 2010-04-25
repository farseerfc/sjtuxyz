/* This file was generated by SableCC (http://www.sablecc.org/). */

package xyzlex.node;

import java.util.*;
import xyzlex.analysis.*;

@SuppressWarnings("nls")
public final class AMethodDecl extends PMethodDecl
{
    private PType _type_;
    private TId _id_;
    private final LinkedList<PArg> _arg_ = new LinkedList<PArg>();
    private PPreDecl _preDecl_;
    private PPostDecl _postDecl_;
    private PBody _body_;
    private PExp _exp_;

    public AMethodDecl()
    {
        // Constructor
    }

    public AMethodDecl(
        @SuppressWarnings("hiding") PType _type_,
        @SuppressWarnings("hiding") TId _id_,
        @SuppressWarnings("hiding") List<PArg> _arg_,
        @SuppressWarnings("hiding") PPreDecl _preDecl_,
        @SuppressWarnings("hiding") PPostDecl _postDecl_,
        @SuppressWarnings("hiding") PBody _body_,
        @SuppressWarnings("hiding") PExp _exp_)
    {
        // Constructor
        setType(_type_);

        setId(_id_);

        setArg(_arg_);

        setPreDecl(_preDecl_);

        setPostDecl(_postDecl_);

        setBody(_body_);

        setExp(_exp_);

    }

    @Override
    public Object clone()
    {
        return new AMethodDecl(
            cloneNode(this._type_),
            cloneNode(this._id_),
            cloneList(this._arg_),
            cloneNode(this._preDecl_),
            cloneNode(this._postDecl_),
            cloneNode(this._body_),
            cloneNode(this._exp_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMethodDecl(this);
    }

    public PType getType()
    {
        return this._type_;
    }

    public void setType(PType node)
    {
        if(this._type_ != null)
        {
            this._type_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._type_ = node;
    }

    public TId getId()
    {
        return this._id_;
    }

    public void setId(TId node)
    {
        if(this._id_ != null)
        {
            this._id_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._id_ = node;
    }

    public LinkedList<PArg> getArg()
    {
        return this._arg_;
    }

    public void setArg(List<PArg> list)
    {
        this._arg_.clear();
        this._arg_.addAll(list);
        for(PArg e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public PPreDecl getPreDecl()
    {
        return this._preDecl_;
    }

    public void setPreDecl(PPreDecl node)
    {
        if(this._preDecl_ != null)
        {
            this._preDecl_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._preDecl_ = node;
    }

    public PPostDecl getPostDecl()
    {
        return this._postDecl_;
    }

    public void setPostDecl(PPostDecl node)
    {
        if(this._postDecl_ != null)
        {
            this._postDecl_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._postDecl_ = node;
    }

    public PBody getBody()
    {
        return this._body_;
    }

    public void setBody(PBody node)
    {
        if(this._body_ != null)
        {
            this._body_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._body_ = node;
    }

    public PExp getExp()
    {
        return this._exp_;
    }

    public void setExp(PExp node)
    {
        if(this._exp_ != null)
        {
            this._exp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._exp_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._type_)
            + toString(this._id_)
            + toString(this._arg_)
            + toString(this._preDecl_)
            + toString(this._postDecl_)
            + toString(this._body_)
            + toString(this._exp_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._type_ == child)
        {
            this._type_ = null;
            return;
        }

        if(this._id_ == child)
        {
            this._id_ = null;
            return;
        }

        if(this._arg_.remove(child))
        {
            return;
        }

        if(this._preDecl_ == child)
        {
            this._preDecl_ = null;
            return;
        }

        if(this._postDecl_ == child)
        {
            this._postDecl_ = null;
            return;
        }

        if(this._body_ == child)
        {
            this._body_ = null;
            return;
        }

        if(this._exp_ == child)
        {
            this._exp_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._type_ == oldChild)
        {
            setType((PType) newChild);
            return;
        }

        if(this._id_ == oldChild)
        {
            setId((TId) newChild);
            return;
        }

        for(ListIterator<PArg> i = this._arg_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PArg) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._preDecl_ == oldChild)
        {
            setPreDecl((PPreDecl) newChild);
            return;
        }

        if(this._postDecl_ == oldChild)
        {
            setPostDecl((PPostDecl) newChild);
            return;
        }

        if(this._body_ == oldChild)
        {
            setBody((PBody) newChild);
            return;
        }

        if(this._exp_ == oldChild)
        {
            setExp((PExp) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
