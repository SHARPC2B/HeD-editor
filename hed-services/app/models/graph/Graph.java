package models.graph;

import play.db.ebean.Model;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * User: rk Date: 7/23/13
 */
@Entity
public class Graph
        extends Model
{

    public String name;

    public List<Triple> triples = new ArrayList<Triple>();

    public void add (Triple t)
    {
        triples.add( t );
    }

}
