package de.ds.codingdojo.mtu;

import java.io.IOException;

public class MtuDetermination {

  public static final int MAX_BUFFER_SIZE = 1500;
  public static final String URL = "google.de";
  private PingExecutor pingExecutor;

  public MtuDetermination(PingExecutor pingExecutor) {
    this.pingExecutor = pingExecutor;
  }

  /**
   * Returns the maximum mtu size.
   *
   * @return
   */
  public int findMaxMtuSize() throws IOException {
    int bufferSize = MAX_BUFFER_SIZE;
    boolean fragmented = pingExecutor.isPingFragmented(bufferSize, URL);
    while (fragmented) {
      bufferSize--;
      fragmented = pingExecutor.isPingFragmented(bufferSize, URL);
    }
    return bufferSize;
  }


}
