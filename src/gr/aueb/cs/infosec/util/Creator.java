package gr.aueb.cs.infosec.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public abstract class Creator {

  private String input;
  private String output;
  private BufferedReader in;
  private BufferedWriter out;
  private Map<String, String> node_pairs;

  public Creator(String input, String output) {
    this.input = input;
    this.output = output;
    this.node_pairs = new HashMap<String, String>();
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
    this.node_pairs.clear();
    this.node_pairs = null;
  }

  public abstract void process();

  public String getInput() {
    return this.input;
  }

  public String getOutput() {
    return this.output;
  }

  public BufferedReader getReader() {
    return this.in;
  }

  public BufferedWriter getWriter() {
    return this.out;
  }

  public Map<String, String> getNodePairs() {
    return this.node_pairs;
  }

  public String[] splitNodeNames(String inputLine) {
    String[] results = new String[3];
    StringTokenizer tokenizer =
        new StringTokenizer((inputLine.split(",")[1]).split("between")[1], "and,(");
    results[0] = tokenizer.nextToken().replaceAll("^\\s+|\\s+$", "");
    String first = (inputLine.split(",")[1]).split(" ")[0].replaceAll("^\\s+|\\s+$", "");
    String second = tokenizer.nextToken().replaceAll("^\\s+|\\s+$", "");
    results[1] = first + "-" + second;
    results[2] = inputLine.split(",")[0];
    return results;
  }
}
