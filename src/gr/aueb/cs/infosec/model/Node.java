package gr.aueb.cs.infosec.model;

public class Node {

  private String name;
  private String connected_node1;
  private String connected_node2;

  public Node(String name, String connected_node1, String connected_node2) {
    this.name = name;
    this.connected_node1 = connected_node1;
    this.connected_node2 = connected_node2;
  }

  public String getName() {
    return this.name;
  }

  public String getConnectedNode1() {
    return this.connected_node1;
  }

  public String getConnectedNode2() {
    return this.connected_node2;
  }
}
