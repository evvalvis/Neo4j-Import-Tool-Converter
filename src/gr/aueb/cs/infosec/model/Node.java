package gr.aueb.cs.infosec.model;

public class Node {

  // node's name
  private String name;
  // first connected node
  private String connected_node1;
  // second connected node
  private String connected_node2;

  /**
   * Constructor
   *
   * @param name
   * @param connected_node1
   * @param connected_node2
   */
  public Node(String name, String connected_node1, String connected_node2) {
    this.name = name;
    this.connected_node1 = connected_node1;
    this.connected_node2 = connected_node2;
  }

  /**
   * Get the node's name
   *
   * @return
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get the first connected Node
   *
   * @return
   */
  public String getFirstConnectedNode() {
    return this.connected_node1;
  }

  /**
   * Get the second connected Node
   *
   * @return
   */
  public String getSecondConnectedNode() {
    return this.connected_node2;
  }

  @Override
  public boolean equals(Object o) {

    // If the object is compared with itself then return true
    if (o == this) {
      return true;
    }

    /*
     * Check if o is an instance of Node null instanceof [type]" also returns false
     */
    if (!(o instanceof Node)) {
      return false;
    }

    // typecast o to Complex so that we can compare data members
    Node node = (Node) o;

    // Compare the data members and return accordingly
    boolean firstCondition = this.name.compareTo(node.name) == 0;
    boolean secondCondition = this.connected_node1.compareTo(node.connected_node1) == 0;
    boolean thirdCondition = this.connected_node2.compareTo(node.connected_node2) == 0;

    return firstCondition && secondCondition && thirdCondition;
  }

}
