package com.example.b07project;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.text.Editable;
import android.widget.EditText;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.example.b07project.Login.LoginContract;
import com.example.b07project.Login.LoginPresenter;
import com.example.b07project.Login.User;
import com.example.b07project.Stores.Store;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    private LoginPresenter presenter;

    @Mock
    LoginContract.Model model;
    @Mock
    LoginContract.View view;

    @Mock
    EditText editText;

    @Mock
    Editable edit;

    @Mock
    View baseView;

    /*@Test
    public void testValidLoginEmptyUsername(){
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.validLogin("", "users", "pass");
        verify(view).setErrorText("Username and/or password cannot be empty.");
    }*/


    @Before
    public void setup() {

        presenter = new LoginPresenter(view, model);
    }
    @Test
    public void testValidLoginEmptyPassword(){
        presenter.validLogin("user", "users", "");
        verify(view).setErrorText("Username and/or password cannot be empty.");
    }

    @Test
    public void testValidLoginSuccess() {
        String username = "user";
        String userType = "users";
        String password = "user";

        doAnswer(invocation -> {
            LoginContract.UsernameExistsCallback callback = invocation.getArgument(2);
            callback.onUsernameExists(true);
            return null;
        }).when(model).usernameExists(eq(username), eq(userType), any(LoginContract.UsernameExistsCallback.class));

        doAnswer(invocation -> {
            LoginContract.PasswordMatchesCallback callback = invocation.getArgument(3);
            callback.onPasswordMatches(true);
            return null;
        }).when(model).passwordMatches(eq(username), eq(userType), eq(password), any(LoginContract.PasswordMatchesCallback.class));

        presenter.validLogin(username, userType, password);

        verify(view).setViewAndActivity(username);
        verify(view, never()).setErrorText(anyString());
    }

    @Test
    public void testValidLoginFailure() {
        String username = "user";
        String userType = "users";
        String password = "user";

        doAnswer(invocation -> {
            LoginContract.UsernameExistsCallback callback = invocation.getArgument(2);
            callback.onUsernameExists(true); //username exists
            return null;
        }).when(model).usernameExists(eq(username), eq(userType), any(LoginContract.UsernameExistsCallback.class));

        doAnswer(invocation -> {
            LoginContract.PasswordMatchesCallback callback = invocation.getArgument(3);
            callback.onPasswordMatches(false);  // Password doesn't match
            return null;
        }).when(model).passwordMatches(eq(username), eq(userType), eq(password), any(LoginContract.PasswordMatchesCallback.class));

        //LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.validLogin(username, userType, password);

        verify(view).setErrorText("Incorrect username or password. Please try again");
        verify(view, never()).setViewAndActivity(anyString());
    }
    //also if both returned false, and if user returned true but password returned false (technically would never happen)
    // total 2 extra
    @Test
    public void testValidNewAccountEmptyUsername(){
        //LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.validNewAccount("user", "users", "", "", "");
        verify(view).setErrorText("All fields must be filled in");
    }

    //empty password as user, then empty user and pass and store and category as store
    // total 5 extra

    @Test
    public void testValidNewAccountEmptyStoreName(){
        //LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.validNewAccount("user", "stores", "user", "", "tech");
        verify(view).setErrorText("All fields must be filled in");
    }

    @Test
    public void testValidNewAccountExistingUsername() {
        String existingUsername = "existingUser";
        String userType = "users";
        String password = "password";
        String storeName = "storeName";
        String category = "category";

        // Simulate username exists
        doAnswer(invocation -> {
            LoginContract.UsernameExistsCallback callback = invocation.getArgument(2);
            callback.onUsernameExists(true);
            return null;
        }).when(model).usernameExists(eq(existingUsername), eq(userType), any(LoginContract.UsernameExistsCallback.class));

        presenter.validNewAccount(existingUsername, userType, password, storeName, category);

        verify(view).setErrorText("Username is already taken, please choose a different one.");
        verify(model).usernameExists(eq(existingUsername), eq(userType), any(LoginContract.UsernameExistsCallback.class));
        //verify(presenter, never()).createAccount(anyString(), anyString(), anyString(), anyString(), anyString());
        verify(view, never()).accountSuccessfulRedirect();
        verify(view, never()).setErrorText("");
    }

    @Test
    public void testValidNewAccountSuccess() {
        String newUsername = "newUser";
        String userType = "users";
        String password = "password";
        String storeName = "storeName";
        String category = "category";

        // Simulate username does not exist
        doAnswer(invocation -> {
            LoginContract.UsernameExistsCallback callback = invocation.getArgument(2);
            callback.onUsernameExists(false);
            return null;
        }).when(model).usernameExists(eq(newUsername), eq(userType), any(LoginContract.UsernameExistsCallback.class));

        LoginPresenter presenterSpy = Mockito.spy(new LoginPresenter(view, model));

        presenterSpy.validNewAccount(newUsername, userType, password, storeName, category);

        // Verify that the createAccount method is called on the presenter
        verify(presenterSpy).createAccount(eq(newUsername), eq(userType), eq(password), eq(storeName), eq(category));

        verify(model).usernameExists(eq(newUsername), eq(userType), any(LoginContract.UsernameExistsCallback.class));
        verify(view).accountSuccessfulRedirect();
        verify(view).setErrorText("");
        verify(view, never()).setErrorText("Username is already taken, please choose a different one.");
    }

    @Test
    public void testCreateAccountUsers() {
        String username = "user";
        String userType = "users";
        String password = "password";
        String storeName = "";
        String category = "";

        presenter.createAccount(username, userType, password, storeName, category);

        // Use ArgumentCaptor to capture the User object passed to addAccount
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(model).addAccount(userCaptor.capture(), eq(userType), eq(username));

        User capturedUser = userCaptor.getValue();
        assertNotNull(capturedUser);
        assertEquals(username, capturedUser.getUsername());
        assertEquals(password, capturedUser.getPassword());
    }


    @Test
    public void testCreateAccountStoreIDUniqueGeneration() {
        String username = "storeUser";
        String userType = "stores";
        String password = "storePassword";
        String storeName = "storeName";
        String category = "storeCategory";

        LoginPresenter presenterSpy = Mockito.spy(new LoginPresenter(view, model));

        // Simulate that store ID doesn't exist
        doAnswer(invocation -> {
            LoginContract.StoreIDExistsCallback callback = invocation.getArgument(1);
            callback.onStoreIDExists(false);
            return null;
        }).when(model).storeIDExists(anyInt(), any(LoginContract.StoreIDExistsCallback.class));

        presenterSpy.createAccount(username, userType, password, storeName, category);

        // Verify that storeIDExists was called only once within the presenter
        verify(presenterSpy, times(1)).storeIDExists(anyInt());

        // Use ArgumentCaptor to capture the Store object passed to addAccount
        ArgumentCaptor<Store> storeCaptor = ArgumentCaptor.forClass(Store.class);
        verify(model).addAccount(storeCaptor.capture(), eq(userType), eq(username));

        Store capturedStore = storeCaptor.getValue();
        assertNotNull(capturedStore);
        assertEquals(storeName, capturedStore.getStore());
        assertEquals(category, capturedStore.getCategory());
        assertEquals(password, capturedStore.getPassword());
    }

    @Test
    public void testCreateAccountStoreIDAlreadyExistsOnce() {
        String username = "storeUser";
        String userType = "stores";
        String password = "storePassword";
        String storeName = "storeName";
        String category = "storeCategory";

        LoginPresenter presenterSpy = Mockito.spy(new LoginPresenter(view, model));

        // Simulate that store ID exists for the first invocation and then doesn't exist
        AtomicInteger invocationCount = new AtomicInteger(0);

        doAnswer(invocation -> {
            LoginContract.StoreIDExistsCallback callback = invocation.getArgument(1);
            //int storeId = storeIdCaptor.getValue();
            if (invocationCount.getAndIncrement() == 0) {
                // Check if store ID is different than the previously generated one
                callback.onStoreIDExists(true);
            } else {
                // Check if store ID is different than the previously generated one
                callback.onStoreIDExists(false);
            }
            return null;
        }).when(model).storeIDExists(anyInt(), any(LoginContract.StoreIDExistsCallback.class));

        presenterSpy.createAccount(username, userType, password, storeName, category);

        // Verify that storeIDExists was called twice within the presenter
        verify(presenterSpy, times(2)).storeIDExists(anyInt());

        ArgumentCaptor<Store> storeCaptor = ArgumentCaptor.forClass(Store.class);
        verify(model).addAccount(storeCaptor.capture(), eq(userType), eq(username));

        Store capturedStore = storeCaptor.getValue();
        assertNotNull(capturedStore);
        assertEquals(storeName, capturedStore.getStore());
        assertEquals(category, capturedStore.getCategory());
        assertEquals(password, capturedStore.getPassword());
    }



}