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

  // input file
  private String input;
  // output file
  private String output;
  // reader
  private BufferedReader in;
  // writer
  private BufferedWriter out;
  // storage, to check for duplicate entries
  private Map<String, String> storage;

  /**
   * Constructor
   *
   * @param input
   * @param output
   */
  public Creator(String input, String output) {
    this.input = input;
    this.output = output;
    this.storage = new HashMap<String, String>();
    this.initialize();
  }

  /**
   * Initializes the reader, the writer and the storage
   */
  public void initialize() {
    try {
      this.in = new BufferedReader(new FileReader(new File(this.input)));
      this.out = new BufferedWriter(new FileWriter(new File(this.output)));
    } catch (IOException io) {
      io.printStackTrace();
    }
  }

  /**
   * Closes the reader and the writer
   */
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

  /**
   * This method will be implemented by the child classes Determines how the input file is going to
   * be processed in order to create the output file.
   */
  public abstract void process();

  /**
   * Get the input file
   *
   * @return
   */
  public String getInput() {
    return this.input;
  }

  /**
   * Get the output file
   *
   * @return
   */
  public String getOutput() {
    return this.output;
  }

  /**
   * Get the reader
   *
   * @return
   */
  public BufferedReader getReader() {
    return this.in;
  }

  /**
   * Get the writer
   *
   * @return
   */
  public BufferedWriter getWriter() {
    return this.out;
  }

  /**
   * Get the storage
   *
   * @return
   */
  public Map<String, String> getStorage() {
    return this.storage;
  }

  /**
   * Given an input line from the csv dataset file, this method processes the line and returns the
   * three nodes participating in the corresponding link.
   *
   * @param inputLine
   * @return a Node array containing the nodes
   */
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

  /**
   * Get the link's name
   * 
   * @param inputLine
   * @return
   */
  public String getLinkName(String inputLine) {
    return inputLine.split(",")[0];
  }
}
