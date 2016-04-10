package gr.aueb.cs.infosec.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class NodeCreator extends Creator {

  // this could also be in the parent class
  private final String CSV_HEADER = "node_name:ID,connected_node,:LABEL";
  private final String LABEL = "Traffic node";
  private Map<String, String> node_pairs;

  public NodeCreator(String input, String output) {
    super(input, output);
    this.node_pairs = new HashMap<String, String>();
  }

  @Override
  public void finalize() {
    super.finalize();
    this.node_pairs.clear();
    this.node_pairs = null;
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
          StringTokenizer tokenizer =
              new StringTokenizer((nextLine.split(",")[1]).split("between")[1], "and,(");
          String node_name = tokenizer.nextToken().replaceAll("^\\s+|\\s+$", "");
          String first = (nextLine.split(",")[1]).split(" ")[0].replaceAll("^\\s+|\\s+$", "");
          String second = tokenizer.nextToken().replaceAll("^\\s+|\\s+$", "");
          String node_pair = first + "-" + second;
          if (this.node_pairs.get(node_name) != null) {
            // we don't want to write duplicate entries
            continue;
          }
          this.node_pairs.put(node_name, node_pair);
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
