package com.wahld.bootstrapxd;

import org.junit.Test;

import java.io.File;
import java.net.URI;

/**
 * Created by dwahl on 5/22/15.
 */
public class TestBootstrapXD {

    @Test
    public void testBootstrapXD() throws Exception{
        URI uri = getClass().getClassLoader().getResource("config.yml").toURI();

        Runner runner = new Runner();
        File file = new File(uri);
    }
}
