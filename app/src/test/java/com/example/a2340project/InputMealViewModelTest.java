package com.example.a2340project;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputMealViewModelTest {

    @Test
    public void getInstance() {
        InputMealViewModel firstInstance = InputMealViewModel.getInstance();
        InputMealViewModel secondInstance = InputMealViewModel.getInstance();

        assertSame("getInstance should return the same instance", firstInstance, secondInstance);
    }

    @Test
    public void getInstance_2() {
        InputMealViewModel instance = InputMealViewModel.getInstance();

        assertNotNull("getInstance should never return null", instance);
    }

    @Test
    public void getUserData() {
        InputMealViewModel viewModel = InputMealViewModel.getInstance();

        assertNotNull("getUserData should not return null", viewModel.getUserData());
    }

    @Test
    public void getUserData_2() {
        InputMealViewModel viewModel = InputMealViewModel.getInstance();
        UserData firstCallUserData = viewModel.getUserData();
        UserData secondCallUserData = viewModel.getUserData();

        assertSame("getUserData should return the same UserData instance on multiple calls", firstCallUserData, secondCallUserData);
    }

    @Test
    public void updateData() {
        InputMealViewModel viewModel = InputMealViewModel.getInstance();

        int feet = 5;
        int inches = 10;
        int weight = 150;
        String gender = "male";

        viewModel.updateData(feet, inches, weight, gender);
        UserData userData = viewModel.getUserData();

        assertEquals("Height feet did not match", feet, userData.getHeightFeet());
        assertEquals("Height inches did not match", inches, userData.getHeightInches());
        assertEquals("Weight did not match", weight, userData.getWeight());
        assertEquals("Gender did not match", gender, userData.getGender());
    }

    @Test
    public void updateData_2() {
        InputMealViewModel viewModel = InputMealViewModel.getInstance();

        viewModel.updateData(6, 0, 200, "female");

        int newFeet = 5;
        int newInches = 5;
        int newWeight = 130;
        String newGender = "male";
        viewModel.updateData(newFeet, newInches, newWeight, newGender);

        UserData userData = viewModel.getUserData();

        assertEquals("Height feet should reflect latest update", newFeet, userData.getHeightFeet());
        assertEquals("Height inches should reflect latest update", newInches, userData.getHeightInches());
        assertEquals("Weight should reflect latest update", newWeight, userData.getWeight());
        assertEquals("Gender should reflect latest update", newGender, userData.getGender());
    }
}