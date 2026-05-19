package m1.dwa.cv.services;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import m1.dwa.cv.daos.BonusDaoMock;
import m1.dwa.cv.daos.PixelDaoMock;
import m1.dwa.cv.daos.SkillDaoMock;
import m1.dwa.cv.daos.UserDaoMock;
import m1.dwa.cv.entities.GameState;
import m1.dwa.cv.entities.Pixel;
import m1.dwa.cv.entities.User;
import m1.dwa.cv.game.Game;
import m1.dwa.cv.game.actions.ClickAction;
import m1.dwa.cv.game.actions.PaintPixelsAction;
import m1.dwa.cv.game.skills.SkillRegistery;
import m1.dwa.cv.notifiers.PlayerNotifierMock;

public class GameServiceTest {

    private UserDaoMock userDao;
    private PixelDaoMock pixelDao;
    private BonusDaoMock bonusDao;
    private SkillDaoMock skillDao;
    private final PlayerNotifierMock playerNotifier = new PlayerNotifierMock();

    private Game game;
    private GameService gameService;

    @Before
    public void reset() throws Exception {
        userDao = new UserDaoMock();
        User user = new User();
        userDao.create(user);

        pixelDao = new PixelDaoMock();
        List<Pixel> pixels = new ArrayList<>();
        for (Pixel pixel : pixelDao.every()) {
            pixels.add(new Pixel(pixel.getX(), pixel.getY(), pixel.getPrice()));
        }

        GameState gameState = new GameState();
        gameState.setPixel(pixels);
        SkillRegistery skillRegistery = new SkillRegistery();
        game = new Game(gameState, skillRegistery);

        gameService = new GameService(userDao, pixelDao, bonusDao, skillDao, game);
        gameService.setPlayerNotifier(playerNotifier);
    }

    @Test
    public void purchasePixels() throws Exception {
        User user = new User();
        user.setCredits(20);
        userDao.update(user);
        gameService.addSession(0);
        playerNotifier.addSession("session_id");

        user = userDao.find(0);
        int[] pixelsToBuyX = { 0, 0 };
        int[] pixelsToBuyY = { 2, 3 };
        gameService.addAction(0,
            new PaintPixelsAction(pixelsToBuyX, pixelsToBuyY, "red")
        );
        game.tick();

        user = userDao.find(0);
        assertTrue("L'utilisateur n'a pas était débité (ou pas de la bonne valeur)", user.getCredits() == 0);
        assertTrue("L'utilisateur ne possède pas les pixels achetés", user.pixelsCount() == 2);
    }

    @Test
    public void clickAction() throws Exception {
        User user = new User();
        user.setCredits(26);
        userDao.update(user);
        gameService.addSession(0);
        playerNotifier.addSession("session_id");

        gameService.addAction(0,
            new ClickAction(10)
        );
        game.tick();

        user = userDao.find(0);
        assertTrue("L'utilisateur n'a pas étatait créditer", user.getCredits() == 36);
    }

    @Test(expected = Exception.class)
    public void addSessionWithInvalidUserId() throws Exception {
        gameService.addSession(999);
    }

    @Test(expected = Exception.class)
    public void addMultipleActionsBeforeTick() throws Exception {
        gameService.addSession(0);
        gameService.addAction(0, new ClickAction(1));
        gameService.addAction(0, new ClickAction(1));
    }

    @Test(expected = Exception.class)
    public void useBonusNotOwned() throws Exception {
        gameService.addSession(0);
        // On suppose que le bonus 99 n'appartient pas au joueur (ou n'existe pas)
        gameService.useBonus(0, 99);
    }
}
