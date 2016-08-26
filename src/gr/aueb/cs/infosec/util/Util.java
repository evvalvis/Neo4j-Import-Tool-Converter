package gr.aueb.cs.infosec.util;

public class Util {

  /**
   * Get the data quality for the input link
   *
   * @param inputLine
   * @return
   */
  public static int getDataQuality(String inputLine) {
    return Integer.parseInt(inputLine.split(",")[6]);
  }

  /**
   * Get the link's name
   *
   * @param inputLine
   * @return
   */
  public static String getLinkName(String inputLine) {
    return inputLine.split(",")[0];
  }

  /**
   * Get the link's flow. About the try catch : the dataset has in some occasions data quality 1
   * entries missing the flow input ...... I just return 0 in that case (since we don't have correct
   * input data we won't have correct output data)
   *
   * @param inputLine
   * @return
   */
  public static double getFlowRate(String inputLine) {
    try {
      return Double.parseDouble(inputLine.split(",")[8]);
    } catch (Exception e) {
      return -1;
    }
  }

  /**
   * Get the date
   *
   * @param nextLine
   * @return
   */
  public static String getDate(String inputLine) {
    return inputLine.split(",")[2].split(" ")[0];
  }

  /**
   * Aaaand another issue with the data set showed up. Some entries have data quality 1 or 2 and the
   * nodes plus the road are missing. Lucky us!
   *
   * @param inputLine
   * @return
   */
  public static boolean checkForEmptyNodes(String inputLine) {
    return inputLine.split(",")[1].trim().length() == 0;
  }
}
