package com.essers.wms.movement.views;

import com.vaadin.flow.component.login.LoginForm;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class LoginViewTest {
    @Autowired
    private LoginView loginView;

   @Test
    void testLoginFormErrorTest() {
        LoginForm loginForm = new LoginForm();
        loginForm.setError(true);
        assertTrue(loginForm.isError());
    }


}