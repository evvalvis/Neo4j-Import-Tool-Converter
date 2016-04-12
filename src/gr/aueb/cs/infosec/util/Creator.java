package gr.aueb.cs.infosec.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gr.aueb.cs.infosec.model.Node;

public abstract class Creator {

  private String input;
  private String output;
  private BufferedReader in;
  private BufferedWriter out;
  private Map<String, String> storage;

  public Creator(String input, String output) {
    this.input = input;
    this.output = output;
    this.storage = new HashMap<String, String>();
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
    this.storage.clear();
    this.storage = null;
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

  public Map<String, String> getStorage() {
    return this.storage;
  }

  public Node[] splitNodeNames(String inputLine) {
    Node[] results = new Node[3];
    // first node
    String first = inputLine.split(",")[1].split("between")[0].replaceAll("^\\s+|\\s+$", "");
    // 2nd node
    String second =
        inputLine.split(",")[1].split("and")[0].split("between")[1].replaceAll("^\\s+|\\s+$", "");
    // 3rd node
    String temp = inputLine.split(",")[1].split("and")[1];
    String third = temp.substring(0, temp.indexOf("(" + this.getLinkName(inputLine) + ")"))
        .replaceAll("^\\s+|\\s+$", "");
    results[0] = new Node(first, second, third);
    results[1] = new Node(second, first, third);
    results[2] = new Node(third, first, second);
    return results;
  }


  public String getLinkName(String inputLine) {
    return inputLine.split(",")[0];
  }
}
