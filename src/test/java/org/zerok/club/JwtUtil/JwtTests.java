package org.zerok.club.JwtUtil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerok.club.util.JwtUtil;

@SpringBootTest
public class JwtTests {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void generateTokenTest() {
        String email = "user10@zerok.org";
        String jwtToken = jwtUtil.generateToken(email);
        System.out.println(jwtToken);

        String decodeEmail = jwtUtil.getEmailByJwtToken(jwtToken);
        System.out.println(decodeEmail);
    }


}
