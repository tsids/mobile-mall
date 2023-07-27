package com.example.b07project;

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

//        this.usernameExists = false;
//        this.passwordMatches = false;

//        model.usernameExists(username, userType);
//        model.passwordMatches(username, userType, password);
        model.checkLogin(username, password, userType);

//        boolean check = false;

//        if (check) {
//            view.setErrorText("");
//        } else {
            view.setErrorText("");
//        }
//        System.out.println("");
    }


    @Override
    public void login() {
        view.setViewAndActivity();
    }

    @Override
    public void invalidLogin()  {
        view.setErrorText("Incorrect username or password. Please try again");
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
            view.setErrorText("Username or password cannot be empty");
        } else if (usernameExists) {
            view.setErrorText("Username is already taken, please choose a different one.");

        } else {

            createAccount(username, userType, password);
            view.accountSuccessfulRedirect();
            view.setSuccessText("Account successfully created. Please log in");
        }
    }

    @Override
    public void createAccount(String username, String userType, String password) {
        //add username, password to database and switch to user navigation

    }


}
