package com.example.b07project;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.text.Editable;
import android.widget.EditText;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.example.b07project.Login.LoginContract;
import com.example.b07project.Login.LoginPresenter;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

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

    @Test
    public void checkEmptyUsernameLogin(){
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.validLogin("", "users", "pass");
        verify(view).setErrorText("Username and/or password cannot be empty.");
    }

    @Test
    public void checkEmptyPasswordLogin(){
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.validLogin("user", "users", "");
        verify(view).setErrorText("Username and/or password cannot be empty.");
    }

    @Test
    public void testValidLogin_Success() {
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

        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.validLogin(username, userType, password);

        verify(view).setViewAndActivity(username);
        verify(view, never()).setErrorText(anyString());
    }





}