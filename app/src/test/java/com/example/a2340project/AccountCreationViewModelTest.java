package com.example.a2340project;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountCreationViewModelTest {

    @Test
    public void getInstance() {
        AccountCreationViewModel firstInstance = AccountCreationViewModel.getInstance();
        AccountCreationViewModel secondInstance = AccountCreationViewModel.getInstance();

        assertSame("Expected getInstance to return the same instance", firstInstance, secondInstance);
    }

    @Test
    public void getAccountCreationData() {
        AccountCreationViewModel viewModel = AccountCreationViewModel.getInstance();

        assertNotNull("Expected getAccountCreationData to return a non-null AccountCreationData instance", viewModel.getAccountCreationData());
    }

    @Test
    public void updateData() {
        AccountCreationViewModel viewModel = AccountCreationViewModel.getInstance();
        String expectedUsername = "testUser";
        String expectedPassword = "testPass";

        viewModel.updateData(expectedUsername, expectedPassword);
        AccountCreationData data = viewModel.getAccountCreationData();

        assertEquals("Expected username to match the updated value", expectedUsername, data.getUsername());
        assertEquals("Expected password to match the updated value", expectedPassword, data.getPassword());
    }
}