package com.example.dictionaryenvi.Account;
import com.backend.Connection.UserDataAccess;
import com.backend.User.User;
import com.example.dictionaryenvi.UserInformation;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import static com.example.dictionaryenvi.Account.Login.currentUser;

public class CheckLoginService extends Service<Boolean> {
    private User user;
    private UserDataAccess userDataAccess;
    private String usernameStr;
    public CheckLoginService(User user , UserDataAccess userDataAccess , String usernameStr) {
        this.user = user;
        this.userDataAccess = userDataAccess;
        this.usernameStr = usernameStr;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                boolean check = userDataAccess.isCorrectAccount(user);
                if(check)   currentUser = userDataAccess.getUserInfo(usernameStr);
                return check;
            }
        };
    }
}
