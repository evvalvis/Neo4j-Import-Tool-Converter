package gr.aueb.cs.infosec.test;

import gr.aueb.infosec.creators.NodeCreator;
import gr.aueb.infosec.creators.RelationshipCreator;

public class Test {

  public static void main(String[] args) {
    String in = "C:\\Users\\Evan\\Desktop\\Data\\Aug 2014.csv";
    String out = "C:\\Users\\Evan\\Desktop\\n.csv";
    String rout = "C:\\Users\\Evan\\Desktop\\r.csv";
    NodeCreator n = new NodeCreator(in, out);
    n.process();
    n.finalize();
    RelationshipCreator r = new RelationshipCreator(in, rout);
    r.process();
    r.finalize();
  }

}
