package models.old;

import play.db.ebean.Model;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * [2013.08.03] Not hooked up to JSON yet.
 *
 * [2013.08.29] Can probably be deleted.
 *
 * User: rk Date: 8/3/13 Package: models
 */
@Entity
public class ArtifactPrimitive
        extends Model
{
    public String id;

    public String template;

    public String description;

    public List<String> uris = new ArrayList<String>();

    public ArtifactPrimitive (final String id,
                              final String template,
                              final String description,
                              final List<String> uris)
    {
        this.id = id;
        this.template = template;
        this.description = description;
        this.uris = uris;
    }
}
