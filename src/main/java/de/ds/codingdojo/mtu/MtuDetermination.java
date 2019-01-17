package de.ds.codingdojo.mtu;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MtuDetermination {

  public static final int MAX_BUFFER_SIZE = 1500;
  public static final int TIMEOUT_IN_MILLIS = 1000;
  private PingExecutor pingExecutor;

  MtuDetermination(PingExecutor pingExecutor) {
    this.pingExecutor = pingExecutor;
  }

  public static MtuDetermination generate() {
    final ProcessBuilder processBuilder = new ProcessBuilder();
    final PingExecutor pingExecutor = new PingExecutor(processBuilder);
    return new MtuDetermination(pingExecutor);
  }

  /**
   * Returns the maximum mtu size.
   *
   * @param host The host to ping
   * @return the maximum non-fragmented mtu for the given host.
   */
  public int findMaxMtuSize(final String host) throws IOException {
    validateParameterNotNullOrEmpty(host);

    // TODO Add new dependency to check host which can be mocked
    boolean isReachable = InetAddress.getByName(host).isReachable(TIMEOUT_IN_MILLIS);

    if (!isReachable) {
      throw new UnknownHostException("host " + host + " is not reachable!");
    }

    if(!pingExecutor.isPingFragmented(MAX_BUFFER_SIZE, host)){
      return MAX_BUFFER_SIZE;
    };
    int currentMin = 0;
    int currentMax = MAX_BUFFER_SIZE;
    int center = currentMax;

    boolean endOfRangeReached = false;
    while (!endOfRangeReached) {
      center = (currentMax + currentMin) / 2;
      boolean fragmented = pingExecutor.isPingFragmented(center, host);

      System.out.println("Min '" + currentMin + "', max '" + currentMax +"', center '" + center +"', fragmented '" + fragmented +"'.");
      endOfRangeReached = (currentMax - currentMin <= 2) && !fragmented;

      if (fragmented){
        currentMax = center;
      }else {
        currentMin = center;
      }
    }
    return center;
  }

  private void validateParameterNotNullOrEmpty(String parameter) {
    if (parameter == null || parameter.isEmpty()) {
      throw new IllegalArgumentException("Parameter host cannot be null or empty. Was '" + parameter + "'.");
    }
  }

}
