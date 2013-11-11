package models;

import java.util.ArrayList;
import java.util.List;

/**
 * User: rk Date: 9/8/13
 */
public class TemplateGroup
        extends SharpType
{
    public String label;

    public List<PrimitiveTemplate> templates = new ArrayList<PrimitiveTemplate>();

}
