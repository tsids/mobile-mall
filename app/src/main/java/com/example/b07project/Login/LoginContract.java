package com.example.b07project.Login;

public interface LoginContract {


    public interface Model{
        public void usernameExists(String username, String userType, LoginContract.UsernameExistsCallback callback);
        public void passwordMatches(String username, String userType, String password, LoginContract.PasswordMatchesCallback callback);
        //public void storeIDExists(int id, LoginContract.StoreIDExistsCallback callback);
        public void setPresenter(LoginContract.Presenter presenter);

        public void addAccount(Object newAccount, String userType, String username);
        //public void checkLogin(String username, String userType, String password);
    }

    public interface Presenter{
        public void validLogin();
        //public void login();
        //public void usernameExists();
        //public void passwordMatches();
        public void validNewAccount();
        void createAccount(String username, String userType, String password, String storeName, String category);
    }

    public interface View{
        public String getUsername();
        public String getPassword();
        public String getUserType();
        public void setErrorText(String message);
        public void setSuccessText(String message);
        public void onClickSwitchToLogin(android.view.View view);
        public void onClickSwitchToNewAccount(android.view.View view);
        void setViewAndActivity(String username);
        void accountSuccessfulRedirect();
        public String getCategory();
        public String getStoreName();
    }

    public interface UsernameExistsCallback {
        void onUsernameExists(boolean exists);
    }

    public interface PasswordMatchesCallback {
        void onPasswordMatches(boolean matches);
    }
    public interface StoreIDExistsCallback {
        void onStoreIDExists(boolean matches);
    }

}
