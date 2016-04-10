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

import gr.aueb.cs.infosec.model.Node;

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

  public Node[] splitNodeNames(String inputLine) {
    Node[] results = new Node[3];
    String delimiters = "between,and,(";
    if (this.getLinkName(inputLine).equals("AL1173A")) {
      delimiters = "between,and";
    }
    StringTokenizer tokenizer = new StringTokenizer((inputLine.split(",")[1]), delimiters, false);
    // used for very few specifically formatted entries
    boolean flag = tokenizer.countTokens() == 5 && this.getLinkName(inputLine).equals("AL1311");
    // first node
    String first = tokenizer.nextToken().replaceAll("^\\s+|\\s+$", "");
    // 2nd node
    String second = tokenizer.nextToken().replaceAll("^\\s+|\\s+$", "");
    if (flag) {
      tokenizer.nextToken();
    }
    // 3rd node
    String third;
    if (getLinkName(inputLine).equals("AL1173A")) {
      String temp = tokenizer.nextToken();
      third = temp.substring(0, temp.indexOf("(")).replaceAll("^\\s+|\\s+$", "");
    } else {
      third = tokenizer.nextToken().replaceAll("^\\s+|\\s+$", "");
    }
    results[0] = new Node(first, second, third);
    results[1] = new Node(second, first, third);
    results[2] = new Node(third, first, second);
    return results;
  }

  public String getLinkName(String inputLine) {
    return inputLine.split(",")[0];
  }
}
