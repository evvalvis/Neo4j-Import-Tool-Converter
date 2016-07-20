package gr.aueb.cs.infosec.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gr.aueb.cs.infosec.model.Attribute;

public class SelectiveAttributeCreator {

  private String inputFile;
  private String output;
  private List<Attribute> searchAttributes;
  private BufferedReader br = null;
  private BufferedWriter bw = null;

  /**
   * Constructor
   *
   * @param inputFile
   * @param output
   * @param searchAttributes
   */
  public SelectiveAttributeCreator(String inputFile, String output, Attribute... searchAttributes) {
    this.inputFile = inputFile;
    this.output = output;
    this.searchAttributes = new ArrayList<Attribute>(Arrays.asList(searchAttributes));
  }

  /**
   * Set the attributed the subgraph is created from.
   *
   * @param searchAttributes
   */
  public void setSearchAttributes(Attribute... searchAttributes) {
    this.searchAttributes = new ArrayList<Attribute>(Arrays.asList(searchAttributes));
  }

  /**
   * Get the attributed the subgraph is created from.
   *
   * @return
   */
  public List<Attribute> getSearchAttribute() {
    return this.searchAttributes;
  }

  /**
   * Set the output csv file
   *
   * @param output
   */
  public void setOutput(String output) {
    this.output = output;
  }

  /**
   * Get the output csv file
   *
   * @return
   */
  public String getOutput() {
    return this.output;
  }

  /**
   * Set the input csv file
   *
   * @param inputFile
   */
  public void setInputFile(String inputFile) {
    this.inputFile = inputFile;
  }

  /**
   * Get the input csv file
   *
   * @return
   */
  public String getInputFile() {
    return this.inputFile;
  }

  /**
   * This method creates the subgraph. First off, it checks if the selected attributes are present
   * in the current graph. Then is locates their positions on the csv header, in order to know which
   * positions to check. Finally, it parses the input file and writes the applicable entries to the
   * output one.
   */
  public void createSubgraph() {
    System.out.println("Starting subgraph creation");
    try {
      this.br = new BufferedReader(new FileReader(new File(this.inputFile)));
      this.bw = new BufferedWriter(new FileWriter(new File(this.output)));

      // save the relationship file's header to use it on the subgraph
      String csv_header = br.readLine();
      // check if the selected attributed are present in the current graph
      if (!checkAttributes(csv_header)) {
        System.out
            .println("Selected attributes are not present in the relationship file provided.");
        br.close();
        bw.close();
        return;
      }

      // this one will help while splitting the attributes
      int[] attributeIndices = locateAttributeIndices(csv_header);
      // write the header to the new file
      bw.write(csv_header);
      bw.write("\n");
      String nextLine = null;

      while ((nextLine = br.readLine()) != null) {
        String[] relationshipAttributes = nextLine.split(",");

        // no need to check for -1 index cause we are sure that all attributes exist
        int attributesMatch = 0;
        for (int i = 0; i < attributeIndices.length; ++i) {
          if (relationshipAttributes[attributeIndices[i]]
              .equalsIgnoreCase(this.searchAttributes.get(i).getValue())) {
            attributesMatch++;
          }
        }
        // we want all the required attribute checks to be true
        if (attributesMatch == attributeIndices.length) {
          bw.write(nextLine);
          bw.write("\n");
        }
      }
    } catch (IOException io) {
      System.out.println(io.getMessage());
      io.printStackTrace(System.out);
    } finally {
      try {
        if (this.br != null)
          br.close();
        if (this.bw != null)
          bw.close();
      } catch (IOException io) {
        System.out.println(io.getMessage());
        io.printStackTrace(System.out);
      }
    }
    System.out.println("Finished subgraph creation");
  }

  /**
   * Locates the positions of the selected attributes
   *
   * @param csv_header
   * @return an array contain the indices after using the split method on the csv header
   */
  private int[] locateAttributeIndices(String csv_header) {
    List<String> header_attributes = Arrays.asList(csv_header.split(","));
    int[] indices = new int[this.searchAttributes.size()];
    for (int i = 0; i < this.searchAttributes.size(); ++i) {
      indices[i] = header_attributes.indexOf(this.searchAttributes.get(i).getType());
    }
    return indices;
  }

  /**
   * Checks if the selected attributes are present in the current graph
   *
   * @param csv_header
   * @return
   */
  private boolean checkAttributes(String csv_header) {
    List<String> header_attributes = Arrays.asList(csv_header.split(","));
    for (Attribute atr : this.searchAttributes) {
      if (!header_attributes.contains(atr.getType()))
        return false;
    }
    return true;
  }

  public static void main(String[] args) {
    Attribute atr1 = new Attribute("date", "2014-08-01");
    Attribute atr2 = new Attribute("hour", "9");
    Attribute atr3 = new Attribute(":START_ID", "M25 J15");
    Attribute atr4 = new Attribute("type", "good");
    String input = "C:\\Users\\Evan\\Desktop\\r.csv";
    String out = "C:\\Users\\Evan\\Desktop\\LUL.csv";
    SelectiveAttributeCreator sac =
        new SelectiveAttributeCreator(input, out, atr1, atr2, atr3, atr4);
    sac.createSubgraph();
  }
}
