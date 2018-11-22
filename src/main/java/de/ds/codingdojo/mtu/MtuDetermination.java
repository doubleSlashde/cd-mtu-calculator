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

        boolean isReachable = InetAddress.getByName(host).isReachable(TIMEOUT_IN_MILLIS);

        if (!isReachable) {
            throw new UnknownHostException("host " + host + " is not reachable!");
        }

        int bufferSize = MAX_BUFFER_SIZE;
        boolean fragmented = pingExecutor.isPingFragmented(bufferSize, host);
        while (fragmented) {
            bufferSize--;
            fragmented = pingExecutor.isPingFragmented(bufferSize, host);
        }
        return bufferSize;
    }

    private void validateParameterNotNullOrEmpty(String parameter) {
        if (parameter == null || parameter.isEmpty()) {
            throw new IllegalArgumentException("Parameter host cannot be null or empty. Was '" + parameter + "'.");
        }
    }

}
