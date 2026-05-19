package m1.dwa.cv.services;

import java.util.HashSet;
import java.util.Set;

import m1.dwa.cv.daos.BonusDao;
import m1.dwa.cv.daos.PixelDao;
import m1.dwa.cv.daos.SkillDao;
import m1.dwa.cv.daos.UserDao;
import m1.dwa.cv.entities.Bonus;
import m1.dwa.cv.entities.Pixel;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.entities.SkillState;
import m1.dwa.cv.entities.User;
import m1.dwa.cv.game.Game;
import m1.dwa.cv.game.actions.Action;
import m1.dwa.cv.game.events.GameEventListener;
import m1.dwa.cv.notifiers.PlayerNotifier;

public class GameService implements GameEventListener {
    private UserDao userDao;
    private PixelDao pixelDao;
    private BonusDao bonusDao;
    private SkillDao skillDao;
    private PlayerNotifier playerNotifier;
    private Game game;

    public GameService(UserDao userDao, PixelDao pixelDao, BonusDao bonusDao, SkillDao skillDao, Game game) {
        this.userDao = userDao;
        this.pixelDao = pixelDao;
        this.bonusDao = bonusDao;
        this.skillDao = skillDao;
        this.game = game;
        this.game.addListener(this);
    }

    public void setPlayerNotifier(PlayerNotifier playerNotifier) {
        this.playerNotifier = playerNotifier;
    }

    public void addSession(int userId) throws Exception {
        User user = userDao.find(userId);
        if (user == null)
            throw new Exception("Le joueur n'existe pas");
        Player player = new Player(user.getCredits());
        player.setUserId(userId);
        game.addPlayer(userId, player);
    }

    public void removeSession(int userId) {
        game.removePlayer(userId);
    }

    public void addAction(int userId, Action action) throws Exception {
        if (!game.addAction(userId, action))
            throw new Exception("Le joueur à déjà une action en attente");
    }

    public void useBonus(int userId, int bonusId) throws Exception {
        User user = userDao.find(userId);
        Bonus bonus = bonusDao.find(bonusId);
        if (user == null)
            throw new Exception("Le joueur n'existe pas");
        if (!user.hasBonus(bonus))
            throw new Exception("Le joueur ne possède pas ce bonus");
        if (!game.addSkill(userId, bonusId))
            throw new Exception("Ce skill est déjà actif");
    }

    @Override
    public void onPixelsUpdate(Set<Pixel> pixels) {
        for (Pixel pixel : pixels) {
            int ownerId = pixel.getOwnerId();
            User owner = userDao.find(ownerId);

            owner.addPixel(pixel);
            pixel.setOwnerId(ownerId);

            userDao.update(owner);
            pixelDao.update(pixel);
        }
        playerNotifier.pixelsUpdated(pixels);
    }

    @Override
    public void onActionFail(int userId, String message) {
        playerNotifier.actionFailed(userId, message);
    }

    @Override
    public void onPlayerUpdate(int userId, Player player) {
        User user = userDao.find(userId);
        user.setCredits(player.getCredits());
        userDao.update(user);
        playerNotifier.playerUpdated(userId, player);
    }

	@Override
	public void onSkillStarted(int userId, SkillState skill) {
        skillDao.create(skill);
	    playerNotifier.skillStarted(userId, skill);
	}

	@Override
	public void onSkillEnded(int userId, SkillState skill) {
        skillDao.delete(skill);
        playerNotifier.skillEnded(userId, skill);
	}

	@Override
	public void onSkillUpdated(SkillState skill) {
        skillDao.update(skill);
	}
}
