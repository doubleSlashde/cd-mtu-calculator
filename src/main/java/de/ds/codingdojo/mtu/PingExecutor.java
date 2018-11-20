package de.ds.codingdojo.mtu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PingExecutor {

  private ProcessBuilder pb;

  public PingExecutor(ProcessBuilder processBuilder) {
    this.pb = processBuilder;
  }

  /**
   * Returns true if package returned fragmented, false otherwise.
   *
   * @param packetSize
   * @param host
   * @return
   */
  public boolean isPingFragmented(final int packetSize, final String host) throws IOException {
    final List<String> commands = createPingCommand(packetSize, host);
    final String result = executeCmd(commands);
    return result.contains("fragm");
  }


  private List<String> createPingCommand(int packetSize, String host) {
    final List<String> commands = new ArrayList<>();
    commands.add("ping");
    commands.add("-f");
    commands.add("-l");
    commands.add(Integer.toString(packetSize));
    commands.add("-n");
    commands.add("1");
    commands.add(host);
    return commands;
  }


  private String executeCmd(final List<String> command)
          throws IOException {
    final Process process = pb.command(command).start();

    final BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

    System.out.println("Here is the standard output of the command:\n");
    final StringBuilder sb = new StringBuilder();
    String s;
    while ((s = stdInput.readLine()) != null) {
      sb.append(s);
    }
    System.out.println(sb);

    return sb.toString();
  }
}
