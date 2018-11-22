package de.ds.codingdojo.mtu;

import java.io.IOException;

public class Main {

    /**
     * Runs the program.
     *
     * @param args the command line arguments. The first argument is used as host to be pinged.
     * @throws IOException if execution of the ping command fails.
     */
    public static void main(final String[] args) throws IOException {
        MtuDetermination mtuDetermination = MtuDetermination.generate();
        final String host = args[0];
        final int maxMtuSize = mtuDetermination.findMaxMtuSize(host);

        System.out.println("Determination of MTU size for host: " + host);
        System.out.println("Max MTU size is: " + maxMtuSize);
    }

}
