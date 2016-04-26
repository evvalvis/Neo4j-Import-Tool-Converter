package gr.aueb.cs.infosec.test;

import gr.aueb.cs.infosec.util.NodeCreator;
import gr.aueb.cs.infosec.util.RelationshipCreator;

public class Test {

  public static void main(String[] args) {
    String in = "C:\\Users\\Evan\\Desktop\\Aug 2014.csv";
    String out = "C:\\Users\\Evan\\Desktop\\Aug 2014(nodes).csv";
    String rout = "C:\\Users\\Evan\\Desktop\\Aug 2014(relationships).csv";
    NodeCreator n = new NodeCreator(in, out);
    n.process();
    n.finalize();
    RelationshipCreator r = new RelationshipCreator(in, rout);
    r.process();
    r.finalize();
  }

}
