package edu.asu.sharpc2b.hed.impl;


public class HeDNamedExpression {

    private String id;

    private String name;

//    private byte[] dox;
    
    private byte[] doxBytes;

    public HeDNamedExpression( String id, String name, byte[] doxBytes ) {
        this.id = id;
        this.name = name;
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
