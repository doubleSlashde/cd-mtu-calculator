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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MtuDeterminationTest {

  @Mock
  private PingExecutor pingExecutorMock;

  @InjectMocks
  private MtuDetermination testee;

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

  private void checkBufferSize(int maxBufferSize) throws IOException {
    when(pingExecutorMock.isPingFragmented(gt(maxBufferSize), Mockito.anyString())).thenReturn(true);

    int result = testee.findMaxMtuSize();

    assertThat(result, is(maxBufferSize));
  }

}