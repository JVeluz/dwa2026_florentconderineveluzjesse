package m1.dwa.cv.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenService {
    private Map<Integer, String> userToToken = new HashMap<>();

    // https://stackoverflow.com/questions/13992972/how-to-create-an-authentication-token-using-java
	public String generateToken(int userId) {
        String token = UUID.randomUUID().toString();
        userToToken.put(userId, token);
        return token;
	}

	public void removeToken(int userId) {
        userToToken.remove(userId);
	}

	public boolean isValidToken(int userId, String token) {
	    return userToToken.containsKey(userId) && userToToken.get(userId).equals(token);
	}
}
