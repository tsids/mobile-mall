package com.example.b07project;

import android.content.Intent;

public class LoginPresenter implements LoginContract.Presenter {


    LoginContract.View view;
    LoginContract.Model model;
    public boolean usernameExists;
    public boolean passwordMatches;

    public LoginPresenter(LoginContract.View view, LoginContract.Model model){
        this.view = view;
        this.model = model;
    }


    @Override
    public void validLogin() {
        String username = view.getUsername();
        String userType = view.getUserType();
        String password = view.getPassword();

        usernameExists = false;
        passwordMatches = false;

        model.usernameExists(username, userType);
        model.passwordMatches(username, userType, password);

        if (usernameExists && passwordMatches) {
            login();
        } else {
            doNotLogIn("Incorrect username or password. Please try again");
        }
    }

    @Override
    public void login() {
        view.setViewAndActivity();
    }

    @Override
    public void doNotLogIn(String message) {
        view.setErrorText(message);

    }

    public void usernameExists() {
        usernameExists = true;
    }

    public void passwordMatches() {
        passwordMatches = true;
    }

    //////////CREATING NEW ACCOUNT FUNCTIONS/////////

    @Override
    public void validNewAccount() {
        String username = view.getUsername();
        String userType = view.getUserType();
        String password = view.getPassword();

        usernameExists = false;

        model.usernameExists(username, userType);
        if (username.equals("") || password.equals("")) {
          doNotCreateAccount("Username or password cannot be empty");
        } else if (usernameExists) {
            doNotCreateAccount("Username is already taken, please choose a different one.");
        } else {
            createAccount(username, userType, password);
        }
    }

    @Override
    public void createAccount(String username, String userType, String password) {
        //add username, password to database and switch to user navigation

    }

    @Override
    public void doNotCreateAccount(String message) {
        view.setErrorText(message);

    }
}
