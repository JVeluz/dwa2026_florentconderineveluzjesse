package m1.dwa.cv.notifiers;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;

import m1.dwa.cv.entities.Pixel;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.entities.SkillState;

public class PlayerNotifierMock implements PlayerNotifier {

    private Set<String> sessions = new HashSet<>();
    private Gson gson = new Gson();

    public void addSession(String sessionId) {
        sessions.add(sessionId);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

	@Override
	public void playerUpdated(int userId, Player player) {
	    System.out.println(String.format("%d <-- %s", userId, gson.toJson(player)));
	}

	@Override
	public void pixelsUpdated(Set<Pixel> pixels) {
        System.out.println(String.format("everyone <-- %s", gson.toJson(pixels)));
	}

	@Override
	public void actionFailed(int userId, String message) {
	    System.out.println(String.format("%d <-- %s", userId, message));
	}

	@Override
	public void skillStarted(int userId, SkillState skill) {
    	System.out.println(String.format("%d <-- bonus %d started !", userId, skill.getBonusId()));
	}

	@Override
	public void skillEnded(int userId, SkillState skill) {
        System.out.println(String.format("%d <-- bonus %d ended !", userId, skill.getBonusId()));
	}
}
