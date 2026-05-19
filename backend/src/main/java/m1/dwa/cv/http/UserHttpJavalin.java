package m1.dwa.cv.http;

import io.javalin.http.Context;
import m1.dwa.cv.entities.User;
import m1.dwa.cv.services.UserService;

public class UserHttpJavalin {
    private UserService userService;

    public UserHttpJavalin(UserService userService) {
        this.userService = userService;
    }

    public void getUsers(Context context) {
        User[] users = userService.getUsers();
        context.json(users);
    }
}
