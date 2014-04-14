package edu.asu.sharpc2b.hed.impl;


import edu.asu.sharpc2b.actions.SharpAction;
import edu.asu.sharpc2b.ops.SharpExpression;

public class HeDAction {

    private String id;

    private String name;

//    private byte[] dox;

    private byte[] doxBytes;

    private SharpAction action;

    public HeDAction( String id, String name, SharpAction sharpAction, byte[] doxBytes ) {
        this.id = id;
        this.name = name;
        this.action = sharpAction;
        this.doxBytes = doxBytes;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public byte[] getDoxBytes() {
        return doxBytes;
    }

    public void setDoxBytes( byte[] dox ) {
        this.doxBytes = dox;
    }


    public SharpAction getAction() {
        return action;
    }

    public void setAction( SharpAction action ) {
        this.action = action;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        HeDAction that = (HeDAction) o;

        if ( !id.equals( that.id ) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "HeDNamedExpression{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}
