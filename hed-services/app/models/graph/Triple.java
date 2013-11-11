package models.graph;

import play.db.ebean.Model;

import javax.persistence.Entity;

/**
 * User: rk Date: 7/23/13
 */
@Entity
public class Triple
        extends Model
{

    public String subject;

    public String predicate;

    public String value;

    public Triple (String subject,
                   String predicate,
                   String value)
    {
        this.subject = subject;
        this.predicate = predicate;
        this.value = value;
    }

}
