package com.example.b07project.Login;

import com.example.b07project.Stores.Store;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPresenter implements LoginContract.Presenter {


    LoginContract.View view;
    LoginContract.Model model;
    public boolean usernameExists;
    public boolean passwordMatches;
    public boolean storeIDExists;
    FirebaseDatabase db;

    public LoginPresenter(LoginContract.View view, LoginContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void validLogin() {
        String username = view.getUsername();
        String userType = view.getUserType();
        String password = view.getPassword();

        this.usernameExists = false;
        this.passwordMatches = false;

        if (username.equals("") || password.equals("")) {
            view.setErrorText("Username and/or password cannot be empty.");
            return;
        }

        // Check if the username exists
        model.usernameExists(username, userType, new LoginContract.UsernameExistsCallback() {
            @Override
            public void onUsernameExists(boolean exists) {
                usernameExists = exists;

                // Check if the password matches
                model.passwordMatches(username, userType, password, new LoginContract.PasswordMatchesCallback() {
                    @Override
                    public void onPasswordMatches(boolean matches) {
                        passwordMatches = matches;

                        // Perform login check after both username and password checks are done
                        if (usernameExists && passwordMatches) {
                            view.setViewAndActivity(username);
                        } else {
                            view.setErrorText("Incorrect username or password. Please try again");
                        }
                    }
                });
            }
        });
    }

    /*public void usernameExists() { usernameExists = true; }*/

    /*public void passwordMatches() {
        passwordMatches = true;
    }*/

    //////////CREATING NEW ACCOUNT FUNCTIONS/////////

    @Override
    public void validNewAccount() {
        String username = view.getUsername();
        String userType = view.getUserType();
        String password = view.getPassword();
        String storeName = view.getStoreName();
        String category = view.getCategory();

        if (username.equals("") || password.equals("") || (userType.equals("stores") && (category.equals("") || storeName.equals("")))) {
            view.setErrorText("All fields must be filled in");
            return;
        }

        this.usernameExists = false;

        //model.usernameExists(username, userType);
        model.usernameExists(username, userType, new LoginContract.UsernameExistsCallback() {
            public void onUsernameExists(boolean exists) {
                usernameExists = exists;

                if (usernameExists) {
                    view.setErrorText("Username is already taken, please choose a different one.");
                } else {
                    createAccount(username, userType, password, storeName, category);
                    view.accountSuccessfulRedirect();
                    view.setErrorText("");
                }
            }
        });
    }

    @Override
    public void createAccount(String username, String userType, String password, String storeName, String category) {
        //add username, password to database and switch to user navigation
        if (userType.equals("users")) {
            //List<Product> cart = new ArrayList<Product>();
            //List<Product> pastOrders = new ArrayList<Product>();
            User user = new User(null, password, null, username);
            model.addAccount(user, userType, username);
        } else {
            //List<Product> products = new ArrayList<Product>();
            /*Random random = new Random();
            int id = random.nextInt(1000000);

            storeIDExists(id);
            while (storeIDExists) {
                id = random.nextInt(1000000);
                storeIDExists(id);
            }*/

            Store store = new Store(category, password, null, storeName, username);
            model.addAccount(store, userType, username);
        }
    }
    /*public void storeIDExists(int id) {
        setStoreIDExists(false);
        model.storeIDExists(id, new LoginContract.StoreIDExistsCallback() {
            public void onStoreIDExists(boolean exists) {
                setStoreIDExists(exists);
            }
        });
    }*/

    public void setStoreIDExists(boolean exists) {
        this.storeIDExists = exists;
    }
}
