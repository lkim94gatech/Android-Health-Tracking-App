package com.example.a2340project;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountCreationDataTest {

    @Test
    public void getUsername() {
        AccountCreationData data = new AccountCreationData();
        String expectedUsername = "testUser";
        data.setUsername(expectedUsername);

        assertEquals("getUsername should return the correct username", expectedUsername, data.getUsername());
    }

    @Test
    public void getPassword() {
        AccountCreationData data = new AccountCreationData();
        String expectedPassword = "testPass";
        data.setPassword(expectedPassword);

        assertEquals("getPassword should return the correct password", expectedPassword, data.getPassword());
    }

    @Test
    public void setUsername() {
        AccountCreationData data = new AccountCreationData();
        String newUsername = "newUser";
        data.setUsername(newUsername);

        assertEquals("setUsername should set the username correctly", newUsername, data.getUsername());
    }

    @Test
    public void setPassword() {
        AccountCreationData data = new AccountCreationData();
        String newPassword = "newPass";
        data.setPassword(newPassword);

        assertEquals("setPassword should set the password correctly", newPassword, data.getPassword());
    }
}