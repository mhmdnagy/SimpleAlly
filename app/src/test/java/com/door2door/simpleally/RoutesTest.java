package com.door2door.simpleally;

import com.door2door.simpleally.data.RoutesDataSource;
import com.door2door.simpleally.data.pojo.Routes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vezikon on 10/19/16.
 */

public class RoutesTest {

    RoutesDataSource dataSource;
    Routes routes;

    @Before
    public void init() {
        dataSource = new RoutesDataSource();
        routes = dataSource.read();
    }

    @Test
    public void testParsingJson() {

        assertNotNull(routes.routes);
        assertTrue(routes.routes.size() > 0);
        assertTrue(routes.routes.get(0).segments.size() > 0);

    }
}
