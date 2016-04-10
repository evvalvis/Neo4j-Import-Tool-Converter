package gr.aueb.cs.infosec.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class RelationshipCreator extends Creator {

  private final String CSV_HEADER = ":START_ID,edge_name,:END_ID,:TYPE";
  private final String RELATIONSHIP_TYPE = "Road_Congestion";

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
        try {
          String[] split_nodes = this.splitNodeNames(nextLine);
          String node_name = split_nodes[0];
          String node_pair = split_nodes[1];
          String link_name = split_nodes[2];
          if (this.getNodePairs().get(link_name) != null) {
            // we want one entry for each edge
            // we are going to configure the value of the relationship later
            continue;
          }
          this.getNodePairs().put(link_name, node_name);
          out.write(node_name);
          out.write(",");
          out.write(link_name);
          out.write(",");
          out.write(node_pair);
          out.write(",");
          out.write(RELATIONSHIP_TYPE);
          out.write("\n");
        } catch (ArrayIndexOutOfBoundsException ai) {
          // file format issue, happens once or twice only, so i just ignore it
          continue;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(
        "Parsed file : " + this.getInput() + " in " + (System.currentTimeMillis() - startTime));
  }

}
