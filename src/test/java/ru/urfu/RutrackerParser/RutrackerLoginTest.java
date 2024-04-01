package ru.urfu.RutrackerParser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RutrackerLoginTest {
    private final RutrackerLogin auth = RutrackerLogin.getInstance();

    @Test
    public void testReachability(){
        Assertions.assertTrue(auth.login());
    }

    @Test
    public void testNonNullCookies(){
        if(!auth.isAuthenticated())
            auth.login();
        Assertions.assertNotNull(auth.getCookies());
    }
}
