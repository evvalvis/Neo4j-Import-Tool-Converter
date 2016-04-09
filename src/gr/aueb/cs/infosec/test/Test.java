package gr.aueb.cs.infosec.test;

import gr.aueb.cs.infosec.util.NodeCreator;

public class Test {

  public static void main(String[] args) {
    String in = "C:\\Users\\Evan\\Desktop\\MAR15.csv";
    String out = "C:\\Users\\Evan\\Desktop\\MAR15(nodes).csv";
    NodeCreator n = new NodeCreator(in, out);
    n.process();
    n.finalize();
  }

}
