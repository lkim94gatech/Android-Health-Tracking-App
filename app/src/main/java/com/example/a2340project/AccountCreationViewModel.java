package com.example.a2340project;

public class AccountCreationViewModel {
    private static AccountCreationViewModel instance;
    private final AccountCreationData accountCreationData;

    // Mostly copied from a template

    // constructor
    public AccountCreationViewModel() {
        accountCreationData = new AccountCreationData();
        this.updateData("", "");
    }

    // maintains a single instance of the model for a device
    public static synchronized AccountCreationViewModel getInstance() {
        if (instance == null) {
            instance = new AccountCreationViewModel();
        }
        return instance;
    }

    // returns accountCreationData (used to store in Firebase)
    public AccountCreationData getAccountCreationData() {
        return accountCreationData; }

    // resets data when page is opened
    public void updateData(String username, String password) {
        accountCreationData.setUsername(username);
        accountCreationData.setPassword(password);
    }

}
