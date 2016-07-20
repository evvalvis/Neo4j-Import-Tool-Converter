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

  public Attribute(String type, String value) {
    this.setType(type);
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public String getType() {
    return this.type;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setType(String type) {
    if (pool.contains(type))
      this.type = type;
    else
      this.type = "";
  }

  public static List<String> getPool() {
    return pool;
  }
}
