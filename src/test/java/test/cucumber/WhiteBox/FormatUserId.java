package test.cucumber.WhiteBox;

import app.App;
import domain.exceptions.UserIdAlreadyInUseExeption;
import domain.exceptions.UserIdDoesNotExistExeption;
import test.cucumber.helpers.UserHelper;
import test.cucumber.helpers.ErrorMessageHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import static org.junit.Assert.assertEquals;


public class FormatUserId {
    private final UserHelper userHelper;
    private final App app;

    private final ErrorMessageHolder errorMessage;
    public GetUserFromIdSteps(UserHelper userHelper, App app, ErrorMessageHolder errorMessage) {
        this.userHelper = userHelper;
        this.app = app;
        this.errorMessage = errorMessage;
    }

    
}
