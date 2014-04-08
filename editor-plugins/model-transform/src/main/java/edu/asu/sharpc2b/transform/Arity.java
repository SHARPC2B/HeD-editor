package edu.asu.sharpc2b.transform;


import sun.print.resources.serviceui_it;

public enum Arity {

    NULLARY( 0 ),
    UNARY( 1 ),
    BINARY( 2 ),
    TERNARY( 3 ),
    NARY( Integer.MAX_VALUE ),
    LITERAL( -99 ),
    MIXED( -1 ),
    UNKNOWN( null );


    Integer num;

    Arity( Integer num ) {
        this.num = num;
    }

    public int getArityValue() {
        return num;
    }

    public static Arity parse( String s ) {
        return parse( Integer.valueOf( s ) );
    }

    public static Arity parse( int val ) {

        switch( val ) {
            case 0 : return NULLARY;
            case 1 : return UNARY;
            case 2 : return BINARY;
            case 3 : return TERNARY;
            case Integer.MAX_VALUE : return NARY;
            case -99 : return LITERAL;
            case -1 : return MIXED;

            default: return MIXED;
        }
    }

}
