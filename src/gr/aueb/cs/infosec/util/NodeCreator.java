package gr.aueb.cs.infosec.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class NodeCreator {

  private final String CSV_HEADER = "node_name:ID,connected_node,:LABEL";
  private final String LABEL = "Traffic node";
  private String input;
  private String output;
  private BufferedReader in;
  private BufferedWriter out;

  public NodeCreator(String input, String output) {
    this.input = input;
    this.output = output;
    this.initialize();
  }

  public void initialize() {
    try {
      this.in = new BufferedReader(new FileReader(new File(this.input)));
      this.out = new BufferedWriter(new FileWriter(new File(this.output)));
    } catch (IOException io) {
      io.printStackTrace();
    }
  }


  @Override
  public void finalize() {
    try {
      if (this.out != null) {
        out.close();
      }
      if (this.in != null) {
        in.close();
      }
    } catch (IOException io) {
      io.printStackTrace();
    }
  }

  public void process() {
    // add execution time
    long startTime = System.currentTimeMillis();
    String nextLine = null;
    try {
      // do not read the header
      this.in.readLine();
      // write the corresponding header to the output
      this.out.write(CSV_HEADER);
      this.out.write("\n");
      while ((nextLine = this.in.readLine()) != null) {
        try {
          StringTokenizer tokenizer =
              new StringTokenizer((nextLine.split(",")[1]).split("between")[1], "and,(");
          String temp = (nextLine.split(",")[1]).split(" ")[0];
          this.out.write(tokenizer.nextToken().replaceAll("^\\s+|\\s+$", ""));
          this.out.write(",");
          this.out.write(temp.replaceAll("^\\s+|\\s+$", ""));
          this.out.write("-");
          this.out.write(tokenizer.nextToken().replaceAll("^\\s+|\\s+$", ""));
          this.out.write(",");
          this.out.write(LABEL);
          this.out.write("\n");
        } catch (ArrayIndexOutOfBoundsException ai) {
          // file format issue, happens once or twice only, so i just ignore it
          continue;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out
        .println("Parsed file : " + this.input + " in " + (System.currentTimeMillis() - startTime));
  }

}
