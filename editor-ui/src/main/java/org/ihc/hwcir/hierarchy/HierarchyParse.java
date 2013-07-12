package org.ihc.hwcir.hierarchy;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HierarchyParse {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        final Map<String, Vertice> nodes = new HashMap<>();
        
        if (args.length > 1) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                // Parse the objects into an Collection
                Collection<Vertice> vertice = mapper.readValue(new File(args[0]), new TypeReference<Collection<Vertice>>() {});
                Collection<Edge> edge = mapper.readValue(new  File ("edges.json"), new TypeReference<Collection<Edge>>() {});

                for (Vertice v : vertice) {
                    nodes.put(v.get_id(), v);
                }
                for (Edge e : edge) {
                    nodes.get(e.get_outV()).addChildren(nodes.get(e.get_inV()));
                }
                
                mapper.writeValue(new File(args[1]), nodes.get("http://www.newmentor.com/cds/rule/NQF-0068#KnowledgeDocument"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("use: HierarchyParse input_file.json output_file.json");
            System.exit(1);
        }

    }

}
