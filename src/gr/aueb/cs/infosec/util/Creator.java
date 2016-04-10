package gr.aueb.cs.infosec.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Creator {

  private String input;
  private String output;
  private BufferedReader in;
  private BufferedWriter out;

  public Creator(String input, String output) {
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
}
