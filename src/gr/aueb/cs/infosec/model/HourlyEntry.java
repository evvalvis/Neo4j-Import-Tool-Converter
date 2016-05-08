package gr.aueb.cs.infosec.model;

import java.util.ArrayList;
import java.util.List;

public class HourlyEntry {

  private String date;
  private String hour;
  private String link;
  private List<Double> flows;
  private String[] nodes;

  /**
   * Constructor
   */
  public HourlyEntry() {
    this.flows = new ArrayList<Double>();
    this.nodes = new String[2];
  }

  /**
   * Set this entry's hour. Entries vary from 1 to 24
   * 
   * @param hour
   */
  public void setHour(String hour) {
    this.hour = hour;
  }

  /**
   * Get this entry's hour
   * 
   * @return
   */
  public String getHour() {
    return this.hour;
  }

  /**
   * Set the nodes . Note : always gonna be 3
   *
   * @param nodes
   */
  public void setNodes(String... nodes) {
    this.nodes[0] = nodes[0];
    this.nodes[1] = nodes[1];
  }

  /**
   * Get the nodes
   *
   * @return
   */
  public String[] getNodes() {
    return this.nodes;
  }

  /**
   * Set the current link
   *
   * @param link
   */
  public void setLink(String link) {
    this.link = link;
  }

  /**
   * Get the current link
   *
   * @return
   */
  public String getLink() {
    return this.link;
  }

  /**
   * Set the date
   *
   * @param date
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * Get the date
   *
   * @return
   */
  public String getDate() {
    return this.date;
  }

  /**
   * Add a specified flow rate to the list
   *
   * @param flow
   */
  public void addFlow(double flow) {
    this.flows.add(flow);
  }

  /**
   * Get the average hourly flow from the quarter entries. Using java 8 stream to calculate the
   * average.
   *
   * @return
   */
  public double getAverageFlow() {
    return (this.flows.stream().mapToDouble(a -> a).average()).getAsDouble();
  }
}
