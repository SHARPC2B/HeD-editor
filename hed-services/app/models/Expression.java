package models;

public class Expression {

    private String name;

    private String expressionIRI;

    public Expression( String exprIRI, String name ) {
        this.expressionIRI = exprIRI;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getExpressionIRI() {
        return expressionIRI;
    }

    public void setExpressionIRI( String expressionIRI ) {
        this.expressionIRI = expressionIRI;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Expression that = (Expression) o;

        if ( !expressionIRI.equals( that.expressionIRI ) ) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Expression{" +
               "name='" + name + '\'' +
               ", expressionIRI='" + expressionIRI + '\'' +
               '}';
    }

    @Override
    public int hashCode() {
        return expressionIRI.hashCode();
    }
}
