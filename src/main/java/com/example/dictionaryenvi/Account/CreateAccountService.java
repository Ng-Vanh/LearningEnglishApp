package com.example.dictionaryenvi.Account;
import com.backend.Connection.UserDataAccess;
import com.backend.User.User;
import com.example.dictionaryenvi.UserInformation;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import static com.example.dictionaryenvi.Account.Login.currentUser;

public class CreateAccountService extends Service<Boolean> {
    private User newUser;
    private UserDataAccess userDataAccess;
    private String usernameStr;
    public CreateAccountService(User newUser , UserDataAccess userDataAccess , String usernameStr) {
        this.newUser = newUser;
        this.userDataAccess = userDataAccess;
        this.usernameStr = usernameStr;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                boolean check = userDataAccess.isExistingUser(usernameStr);
                if(!check)   userDataAccess.insert(newUser);
                return check;
            }
        };
    }
}
