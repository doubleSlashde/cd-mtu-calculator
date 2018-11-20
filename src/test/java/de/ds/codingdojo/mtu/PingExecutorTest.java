package de.ds.codingdojo.mtu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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
  public void successFullPingShouldReturnTrue() throws IOException {
    //
    when(processBuilderMock.command(anyList())).thenReturn(processBuilderMock);
    when(processBuilderMock.start()).thenReturn(processMock);
    final String expectedOutput = "Ping wird ausgef�hrt f�r google.de [172.217.21.227] mit 1200 Bytes Daten:Paket m�sste fragmentiert werden, DF-Flag ist jedoch gesetzt.Ping-Statistik f�r 172.217.21.227:    Pakete: Gesendet = 1, Empfangen = 0, Verloren = 1    (100% Verlust),";
    when(processMock.getInputStream()).thenReturn(new ByteArrayInputStream(expectedOutput.getBytes()));

    testee = new PingExecutor(processBuilderMock);

    assertTrue(testee.isPingFragmented(1400, "google.de"));
  }

}