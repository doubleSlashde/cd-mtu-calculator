package de.ds.codingdojo.mtu;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MtuDeterminationIT {

    MtuDetermination testee;

    @Before
    public void setUp() {
        testee = MtuDetermination.generate();
    }

    @Test
    public void testIT() throws IOException {
        assertThat(testee.findMaxMtuSize("www.google.de"), is(1472));
    }

    @Test(expected = UnknownHostException.class)
    public void testIT2() throws IOException {
        testee.findMaxMtuSize("file");
    }

}
