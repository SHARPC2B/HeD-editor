package edu.asu.sharpc2b.hed.impl;


import edu.asu.sharpc2b.ops.SharpExpression;

public class HeDNamedExpression {

    private String id;

    private String name;

//    private byte[] dox;
    
    private byte[] doxBytes;

    private SharpExpression expression;

    public HeDNamedExpression( String id, String name, SharpExpression sharpExpression, byte[] doxBytes ) {
        this.id = id;
        this.name = name;
        this.expression = sharpExpression;
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

    public SharpExpression getExpression() {
        return expression;
    }

    public void setExpression( SharpExpression expression ) {
        this.expression = expression;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        HeDNamedExpression that = (HeDNamedExpression) o;

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
