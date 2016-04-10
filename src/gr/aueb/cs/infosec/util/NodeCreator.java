package gr.aueb.cs.infosec.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class NodeCreator extends Creator {

  // this could also be in the parent class
  private final String CSV_HEADER = "node_name:ID,connected_node,:LABEL";
  private final String LABEL = "Traffic node";

  public NodeCreator(String input, String output) {
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
          if (this.getNodePairs().get(node_name) != null) {
            // we don't want to write duplicate entries
            continue;
          }
          this.getNodePairs().put(node_name, node_pair);
          out.write(node_name);
          out.write(",");
          out.write(node_pair);
          out.write(",");
          out.write(LABEL);
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
