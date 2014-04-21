package models;


import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User: rk Date: 8/13/13 Package: models
 */
public class PrimitiveTemplate extends SharpType {

    @Id
    public String templateId;

    public String key;

    public List<String> category;

    public String group;

    public String description;

    public String example;

    public List<String> parameterIds = new ArrayList<String>();

    public List<ParameterType> parameters = new ArrayList<ParameterType>();

    //========================================================================================

}
