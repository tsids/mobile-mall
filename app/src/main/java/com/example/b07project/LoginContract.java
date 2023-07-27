package com.example.b07project;

public interface LoginContract {


    public interface Model{
        public void usernameExists(String username, String userType);
        public void passwordMatches(String username, String userType, String password);
        public void setPresenter(LoginContract.Presenter presenter);
        public void checkLogin(String username, String userType, String password);
    }

    public interface Presenter{
        public void validLogin();
        public void login();

        public void invalidLogin();
        public void usernameExists();
        public void passwordMatches();
        public void validNewAccount();
        public void createAccount(String username, String userType, String password);
    }

    public interface View{
        public String getUsername();
        public String getPassword();
        public String getUserType();
        public void setErrorText(String message);
        public void setSuccessText(String message);
        public void onClickSwitchToLogin(android.view.View view);
        public void onClickSwitchToNewAccount(android.view.View view);

        void setViewAndActivity();

        void accountSuccessfulRedirect();
    }

}
