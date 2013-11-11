package models;

public class NamedConcept {

    private String id;
    private String name;

    public NamedConcept( String id, String name ) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        NamedConcept that = (NamedConcept) o;

        if ( !id.equals( that.id ) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "NamedConcept{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}
