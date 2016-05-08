package gr.aueb.infosec.creators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import gr.aueb.cs.infosec.model.Node;
import gr.aueb.cs.infosec.util.Util;

public class RelationshipCreator extends Creator {

  // output csv header
  private final String CSV_HEADER = ":START_ID,edge_name,middle_node,:END_ID,:TYPE,impact";
  // relationship type for the neo4j database
  private final String RELATIONSHIP_TYPE = "Road_Congestion";
  // temporary variable for current node array
  private Node[] currentNodes;
  // temporary variable for current link name
  private String currentLink;

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
        // we wanna keep only 1 and 2 quality data
        if (Util.getDataQuality(nextLine) > 2) {
          continue;
        }
        Node[] split_nodes = this.splitNodeNames(nextLine);
        String link_name = Util.getLinkName(nextLine);
        double flow = Util.getFlowRate(nextLine);

        if (this.getStorage().containsKey(link_name)) {
          this.getFlowRateStorage().get(link_name).add(flow);
          continue;
        } else {
          // write the previous and init the new link
          if (this.currentLink != null && this.currentNodes != null) {
            this.write(this.currentNodes, this.currentLink);
          }
          this.getStorage().put(link_name, split_nodes[0].getName());
          this.getFlowRateStorage().put(link_name, new ArrayList<Double>());
          this.getFlowRateStorage().get(link_name).add(flow);
          this.currentNodes = split_nodes;
          this.currentLink = link_name;
        }
        // right here all the lines referring to the link have been read so we write the results
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(
        "Parsed file : " + this.getInput() + " in " + (System.currentTimeMillis() - startTime));
  }

  private void write(Node[] currentNodes, String currentLink) throws IOException {
    BufferedWriter out = this.getWriter();
    for (Node node : currentNodes) {
      out.write(node.getName());
      out.write(",");
      out.write(currentLink);
      out.write(",");
      out.write(node.getFirstConnectedNode());
      out.write(",");
      out.write(node.getSecondConnectedNode());
      out.write(",");
      out.write(RELATIONSHIP_TYPE);
      out.write(",");
      out.write(new Double(this.getImpact(currentLink)).toString());
      out.write("\n");
    }
  }
}
