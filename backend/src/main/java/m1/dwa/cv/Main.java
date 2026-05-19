package m1.dwa.cv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.javalin.Javalin;
import io.javalin.json.JavalinGson;
import m1.dwa.cv.daos.BonusDaoJBDC;
import m1.dwa.cv.daos.BonusDaoMock;
import m1.dwa.cv.daos.PixelDaoJBDC;
import m1.dwa.cv.daos.PixelDaoMock;
import m1.dwa.cv.daos.SkillDaoJBDC;
import m1.dwa.cv.daos.SkillDaoMock;
import m1.dwa.cv.daos.UserDaoJDBC;
import m1.dwa.cv.daos.UserDaoMock;
import m1.dwa.cv.entities.GameState;
import m1.dwa.cv.entities.Pixel;
import m1.dwa.cv.entities.SkillState;
import m1.dwa.cv.entities.User;
import m1.dwa.cv.game.Game;
import m1.dwa.cv.game.skills.SkillRegistery;
import m1.dwa.cv.http.AccountHttpJavalin;
import m1.dwa.cv.http.BonusHttpJavalin;
import m1.dwa.cv.http.PixelHttpJavalin;
import m1.dwa.cv.http.PlayerHttpJavalin;
import m1.dwa.cv.http.UserHttpJavalin;
import m1.dwa.cv.services.AccountService;
import m1.dwa.cv.services.BonusService;
import m1.dwa.cv.services.GameService;
import m1.dwa.cv.services.PixelService;
import m1.dwa.cv.services.TokenService;
import m1.dwa.cv.services.UserService;
import m1.dwa.cv.websockets.PlayerSocketJavalin;

// https://www.happycoders.eu/software-craftsmanship/hexagonal-architecture/

public class Main {
    public static void main(String[] args) {
        // Adapteurs entrant*
        UserDaoMock userDao = new UserDaoMock();
        PixelDaoMock pixelDao = new PixelDaoMock();
        BonusDaoMock bonusDao = new BonusDaoMock();
        SkillDaoMock skillDao = new SkillDaoMock();

        // Application
        List<Pixel> pixels = new ArrayList<>();
        for (Pixel pixel : pixelDao.every()) {
            pixels.add(pixel);
        }

        Map<Integer, List<SkillState>> skills = new HashMap<>();
        for (SkillState skillStateJpa : skillDao.every()) {
            User user = skillStateJpa.getUser();
            int userId = user.getId();
            int bonusId = skillStateJpa.getBonusId();
            int currentTick = skillStateJpa.getCurrentTick();
            Map<String, Object> memory = skillStateJpa.getMemory();

            SkillState skillState = new SkillState(bonusId, currentTick);
            skillState.setMemory(memory);

            if (skills.get(userId) == null)
                skills.put(userId, new LinkedList<>());
            skills.get(userId).add(skillState);
        }

        GameState gameState = new GameState();
        gameState.setPixel(pixels);
        gameState.setSkills(skills);

        SkillRegistery skillRegistery = new SkillRegistery();

        Game game = new Game(gameState, skillRegistery);

        // Ports
        UserService userService = new UserService(userDao);
        AccountService accountService = new AccountService(userDao);
        TokenService tokenService = new TokenService();
        BonusService bonusService = new BonusService(bonusDao, userDao);

        PixelService pixelService = new PixelService(pixelDao);
        GameService gameService = new GameService(userDao, pixelDao, bonusDao, skillDao, game);

        // Adapteurs sortant
        UserHttpJavalin userHttp = new UserHttpJavalin(userService);
        PixelHttpJavalin pixelHttp = new PixelHttpJavalin(pixelDao);
        BonusHttpJavalin bonusHttp = new BonusHttpJavalin(bonusDao);
        AccountHttpJavalin accountHttp = new AccountHttpJavalin(accountService, tokenService);
        PlayerHttpJavalin playerHttp = new PlayerHttpJavalin(bonusService);

        // Websocket (adapteur entrant et sortant)
        PlayerSocketJavalin playerSocket = new PlayerSocketJavalin(gameService, pixelService, tokenService);
        gameService.setPlayerNotifier(playerSocket);

        // Lancement de l'application
        Javalin.create(config -> {
            // https://javalin.io/documentation#getting-started
            config.routes.get("/", context -> context.result("Hello World"));
            config.routes.post("/login", accountHttp::postLogin);
            config.routes.post("/register", accountHttp::postRegister);
            config.routes.get("/users", userHttp::getUsers);
            config.routes.get("/pixels", pixelHttp::getPixels);
            config.routes.get("/bonuses", bonusHttp::getBonuses);
            config.routes.post("/player/purchase-bonus", playerHttp::postPurchaseBonus);

            // Pour catch toutes les exceptions venant des méthodes HTTP
            // https://javalin.io/documentation#wrapper-handlers
            config.router.handlerWrapper(endpoint -> {
                if (endpoint.method.isHttpMethod()) {
                    return context -> {
                        try {

                            endpoint.handler.handle(context);
                        } catch (Exception e) {
                            System.err.println(
                                    String.format("Erreur HTTP : \n\tfrom: %s\n\tpath: %s\n\tmessage: %s\n\tbody: %s",
                                            context.host(), context.path(), e.getMessage(), context.body()));
                            context.status(500);
                            context.json(e.getMessage());
                        }
                    };
                }
                return endpoint.handler;
            });

            // https://javalin.io/documentation#wsendpoint
            config.routes.ws("/game", ws -> {
                ws.onConnect(playerSocket::onConnect);
                ws.onClose(playerSocket::onClose);
                ws.onMessage(playerSocket::onMessage);
                ws.onError(context -> {
                    System.err.println(context.error().getMessage());
                });
            });

            // https://javalin.io/documentation#configuring-the-json-mapper
            // Le mapper par défaut lance une erreur si on parse le json dans un objet qui
            // n'a pas pour chaque clef une propriétés associé.
            // Dans playerSocketIn.onMessage(context) on route les messages selon la clef
            // event, vers des fonctions spécialisé qui ont des arguments différent.
            // => Avant d'avoir parse une première fois contexte.message pour lire "event",
            // on ne peut pas savoir de quels paramètres on va avoir besoin.
            // => On change de mapper
            // https://javadoc.io/static/io.javalin/javalin/7.0.0-beta.2/io/javalin/json/JavalinGson.html
            // On utilise gson car c'est l'exemple que javalin propose et il se trouve que
            // javalin à déjà une classe mapper prête.
            config.jsonMapper(new JavalinGson());
        }).start(7070);

        // Lancement du jeu
        // https://gist.github.com/odai-alali/916d643a13525e1e9a3b2b4516ad1236
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                game.tick();
            }
        }, 0, 1000);
    }
}
