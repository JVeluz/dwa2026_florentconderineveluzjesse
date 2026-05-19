package m1.dwa.cv.http;

import io.javalin.http.Context;
import m1.dwa.cv.entities.User;
import m1.dwa.cv.services.AccountService;
import m1.dwa.cv.services.TokenService;


public class AccountHttpJavalin {
    private static class PostLoginBody {
        public String pseudo;
        public String password;
    }
    private static class PostLoginResponse {
        @SuppressWarnings("unused")
		public String token;
        @SuppressWarnings("unused")
		public User user;
    }
    private static class PostRegisterBody {
        public String pseudo;
        public String password;
    }

    private AccountService accountService;
    private TokenService tokenService;

    public AccountHttpJavalin(AccountService accountService, TokenService tokenService) {
        this.accountService = accountService;
        this.tokenService = tokenService;
    }

    public void postLogin(Context context) throws Exception {
        PostLoginBody body = context.bodyAsClass(PostLoginBody.class);
        User user = accountService.login(body.pseudo, body.password);
        if (user == null)
            throw new Exception("Compte inexistant ou mot de passe incorrect");
        String token = tokenService.generateToken(user.getId());
        context.status(200);
        PostLoginResponse response = new PostLoginResponse();
        response.token = token;
        response.user = user;
        context.json(response);
    }

    public void postRegister(Context context) throws Exception {
        PostRegisterBody body = context.bodyAsClass(PostRegisterBody.class);
        if (!accountService.register(body.pseudo, body.password))
            throw new Exception("Pseudo déjà pris");
        context.status(200);
    }
}
