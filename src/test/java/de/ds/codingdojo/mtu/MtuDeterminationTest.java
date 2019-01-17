package de.ds.codingdojo.mtu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.AdditionalMatchers.leq;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MtuDeterminationTest {

  @Mock
  private PingExecutor pingExecutorMock;

  @InjectMocks
  private MtuDetermination testee;

  @Test
  public void testFindMaxMtuSizeNumberOfPingCalls() throws IOException {
    checkBufferSize(1500);

    verify(pingExecutorMock, times(1)).isPingFragmented(anyInt(), anyString());
  }

  @Test
  public void testFindMaxMtuSizeNumberOfPingCalls12_1() throws IOException {
    checkBufferSize(750);

    verify(pingExecutorMock, times(12)).isPingFragmented(anyInt(), anyString());
  }

  @Test
  public void testFindMaxMtuSizeNumberOfPingCalls12_2() throws IOException {
    checkBufferSize(1125);

    verify(pingExecutorMock, times(12)).isPingFragmented(anyInt(), anyString());
  }

  @Test
  public void testFindMaxMtuSizeNumberOfPingCalls12_3() throws IOException {
    checkBufferSize(1125);

    verify(pingExecutorMock, times(12)).isPingFragmented(anyInt(), anyString());
  }

  @Test
  public void testFindMaxMtuSizeNumberOfPingCalls13() throws IOException {
    checkBufferSize(999);

    verify(pingExecutorMock, times(13)).isPingFragmented(anyInt(), anyString());
  }

  @Test
  public void testFindMaxMtuSizeNumberOfPingCalls11() throws IOException {
    checkBufferSize(1126);

    verify(pingExecutorMock, times(11)).isPingFragmented(anyInt(), anyString());
  }

  @Test
  public void testFindMaxMtuSize() throws IOException {
    final int maxBufferSize = 1042;
    checkBufferSize(maxBufferSize);
  }

  @Test
  public void testOtherFindMaxMtuSize() throws IOException {
    final int maxBufferSize = 1045;
    checkBufferSize(maxBufferSize);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyHostShouldThrowIllegalArgumentException() throws IOException {
    final int maxBufferSize = 1045;
    checkBufferSize(maxBufferSize, "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullHostShouldThrowIllegalArgumentException() throws IOException {
    final int maxBufferSize = 1045;
    checkBufferSize(maxBufferSize, null);
  }

  private void checkBufferSize(final int maxBufferSize) throws IOException{
    checkBufferSize(maxBufferSize, "www.google.de");
  }

  private void checkBufferSize(final int maxBufferSize, final String host) throws IOException {
    when(pingExecutorMock.isPingFragmented(gt(maxBufferSize), Mockito.anyString())).thenReturn(true);

    int result = testee.findMaxMtuSize(host);

    assertThat(result, is(maxBufferSize));
  }



}
