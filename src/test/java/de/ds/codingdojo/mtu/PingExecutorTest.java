package de.ds.codingdojo.mtu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PingExecutorTest {

  @Mock
  private ProcessBuilder processBuilderMock;
  @Mock
  private Process processMock;

  private PingExecutor testee;


  @Test
  public void fragmentedPingShouldReturnTrue() throws IOException {

    setPingOutput("Ping wird ausgef�hrt f�r google.de [172.217.21.227] mit 1200 Bytes Daten:Paket m�sste fragmentiert werden, DF-Flag ist jedoch gesetzt.Ping-Statistik f�r 172.217.21.227:    Pakete: Gesendet = 1, Empfangen = 0, Verloren = 1    (100% Verlust),");

    testee = new PingExecutor(processBuilderMock);

    assertTrue(testee.isPingFragmented(1200, "google.de"));
  }

  @Test
  public void unfragmentedPingShouldReturnFalse() throws IOException {

    setPingOutput("Antwort von 10.168.11.200: Bytes=300 Zeit=1ms TTL=128");

    testee = new PingExecutor(processBuilderMock);

    assertFalse(testee.isPingFragmented(1200, "google.de"));
  }

  private void setPingOutput(String expectedOutput) throws IOException {
    when(processBuilderMock.command(anyList())).thenReturn(processBuilderMock);
    when(processBuilderMock.start()).thenReturn(processMock);
    when(processMock.getInputStream()).thenReturn(new ByteArrayInputStream(expectedOutput.getBytes()));
  }

}
