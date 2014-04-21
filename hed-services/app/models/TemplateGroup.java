package models;

import java.util.ArrayList;
import java.util.List;

public class TemplateGroup extends SharpType implements Comparable<TemplateGroup> {

    public String label;
    public List<PrimitiveTemplate> templates = new ArrayList<>();

    private int cnt = 0;

    public int nextId() {
        return cnt++;
    }

    @Override
    public int compareTo( TemplateGroup o ) {
        return this.label.compareTo( o.label );
    }
}
