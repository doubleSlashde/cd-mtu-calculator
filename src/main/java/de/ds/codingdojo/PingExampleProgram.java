package de.ds.codingdojo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PingExampleProgram {


  public static void main(final String args[])
          throws IOException {
    // create the ping command as a list of strings
    final PingExampleProgram ping = new PingExampleProgram();
    ping.doOnePing(1200, "google.de");
  }

  private void doOnePing(int packetSize, String host) throws IOException {
    final List<String> commands = new ArrayList<>();
    commands.add("ping");
    commands.add("-f");
    commands.add("-l");
    commands.add(Integer.toString(packetSize));
    commands.add("-n");
    commands.add("1");
    commands.add(host);
    doCommand(commands);
  }


  private void doCommand(final List<String> command)
          throws IOException {
    String s = null;

    final ProcessBuilder pb = new ProcessBuilder(command);
    final Process process = pb.start();

    final BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
    final BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

    // read the output from the command
    System.out.println("Here is the standard output of the command:\n");
    StringBuilder sb = new StringBuilder();
    while ((s = stdInput.readLine()) != null) {
      sb.append(s);
//      System.out.println(s);
    }
    System.out.println(sb);

    System.out.println(sb.toString().contains("fragm"));

    // read any errors from the attempted command
    System.out.println("Here is the standard error of the command (if any):\n");
    while ((s = stdError.readLine()) != null) {
      System.out.println(s);
    }
  }

}
