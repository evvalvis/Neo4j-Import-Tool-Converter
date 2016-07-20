package gr.aueb.cs.infosec.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gr.aueb.infosec.creators.RelationshipCreator;

public class Attribute {

  private String type;
  private String value;
  private static final List<String> pool =
      new ArrayList<String>(Arrays.asList(RelationshipCreator.getCSVHeader().split(",")));

  /**
   * Constructor
   * 
   * @param type
   * @param value
   */
  public Attribute(String type, String value) {
    this.setType(type);
    this.value = value;
  }

  /**
   * Get the attribute's value
   * 
   * @return
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Get the attribute's type
   * 
   * @return
   */
  public String getType() {
    return this.type;
  }

  /**
   * Set the attribute's value
   * 
   * @param value
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Set the attribute's type
   * 
   * @param type
   */
  public void setType(String type) {
    if (pool.contains(type))
      this.type = type;
    else
      this.type = "";
  }

  /**
   * Get access to the attribute pool
   * 
   * @return
   */
  public static List<String> getPool() {
    return pool;
  }
}
