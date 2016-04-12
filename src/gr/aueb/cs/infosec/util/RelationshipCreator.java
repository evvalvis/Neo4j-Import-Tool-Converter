package gr.aueb.cs.infosec.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import gr.aueb.cs.infosec.model.Node;

public class RelationshipCreator extends Creator {

  // output csv header
  private final String CSV_HEADER = ":START_ID,edge_name,middle_node,:END_ID,:TYPE";
  // relationship type for the neo4j database
  private final String RELATIONSHIP_TYPE = "Road_Congestion";

  /**
   * Constructor
   * 
   * @param input
   * @param output
   */
  public RelationshipCreator(String input, String output) {
    super(input, output);
  }

  @Override
  public void process() {
    // add execution time
    long startTime = System.currentTimeMillis();
    String nextLine = null;
    BufferedReader in = this.getReader();
    BufferedWriter out = this.getWriter();
    try {
      // do not read the header
      in.readLine();
      // write the corresponding header to the output
      out.write(CSV_HEADER);
      out.write("\n");
      while ((nextLine = in.readLine()) != null) {
        Node[] split_nodes = this.splitNodeNames(nextLine);
        String link_name = this.getLinkName(nextLine);
        if (this.getStorage().get(link_name) != null) {
          continue;
        }
        this.getStorage().put(link_name, split_nodes[0].getName());
        for (Node node : split_nodes) {
          out.write(node.getName());
          out.write(",");
          out.write(link_name);
          out.write(",");
          out.write(node.getFirstConnectedNode());
          out.write(",");
          out.write(node.getSecondConnectedNode());
          out.write(",");
          out.write(RELATIONSHIP_TYPE);
          out.write("\n");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(
        "Parsed file : " + this.getInput() + " in " + (System.currentTimeMillis() - startTime));
  }
}
