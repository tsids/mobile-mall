package com.example.b07project;

public interface LoginContract {


    public interface Model{
        public void usernameExists(String username, String userType);
        public void passwordMatches(String username, String userType, String password);
        public void setPresenter(LoginContract.Presenter presenter);

    }

    public interface Presenter{
        public void validLogin();
        public void login();
        public void doNotLogIn(String message);
        public void usernameExists();
        public void passwordMatches();
        public void validNewAccount();
        public void createAccount(String username, String userType, String password);
        public void doNotCreateAccount(String message);
    }

    public interface View{
        public String getUsername();
        public String getPassword();
        public String getUserType();
        public void setErrorText(String message);

        void setViewAndActivity();
    }

}
