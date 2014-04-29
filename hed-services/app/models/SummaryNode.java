package models;

import java.util.ArrayList;
import java.util.List;

public class SummaryNode {
    public String name;
    public List<SummaryNode> children = new ArrayList<SummaryNode>();
    public int size = 100;
}
