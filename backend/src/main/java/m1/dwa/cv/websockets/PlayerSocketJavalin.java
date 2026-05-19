package m1.dwa.cv.websockets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;
import m1.dwa.cv.entities.Pixel;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.entities.SkillState;
import m1.dwa.cv.game.actions.ClickAction;
import m1.dwa.cv.game.actions.PaintPixelsAction;
import m1.dwa.cv.notifiers.PlayerNotifier;
import m1.dwa.cv.services.GameService;
import m1.dwa.cv.services.PixelService;
import m1.dwa.cv.services.TokenService;

public class PlayerSocketJavalin implements PlayerNotifier {
    private static class OnMessageParams {
        public String event;
    }
    private static class OnJoinGameParams {
        public int userId;
        public String token;
    }
    private static class OnClicksParams {
        public int clicks;
    }
    private static class OnPaintPixelsParams {
        public int[] pixelsX;
        public int[] pixelsY;
        public String colorHexadecimal;
    }
    private static class OnUseBonusParams {
        public int bonusId;
    }

    private GameService gameService;
    private PixelService pixelService;
    private TokenService tokenService;
    private Map<String, Integer> sessionToUser = new HashMap<>();
    private Map<Integer, WsContext> userToContext = new HashMap<>();

    public PlayerSocketJavalin(GameService gameService, PixelService pixelService, TokenService tokenService) {
        this.gameService = gameService;
        this.pixelService = pixelService;
        this.tokenService = tokenService;
    }

    public void onConnect(WsConnectContext context) {
        Pixel[] pixels = pixelService.every();
        context.send(pixels);
        context.enableAutomaticPings();
    }

    public void onClose(WsCloseContext context) {
        Integer userId = sessionToUser.remove(context.sessionId);
        if (userId != null) {
            gameService.removeSession(userId);
        }
    }

    public void onMessage(WsMessageContext context) {
        try {
            OnMessageParams params = context.messageAsClass(OnMessageParams.class);
            switch (params.event) {
                case "join-game":
                    onJoinGame(context);
                    break;
                case "leave-game":
                    onLeaveGame(context);
                    break;
                case "clicks-action":
                    onClicks(context);
                    break;
                case "paint-pixels-action":
                    onPaintPixels(context);
                    break;
                case "use-bonus":
                    onUseBonus(context);
                    break;
                case "ping":
                    context.send("{\"message\": \"pong\"}");
                    break;
                default:
                    context.send("{\"error\": \"event inconnu\"}");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            context.send("{\"error\": \"Requête mal formée\"}");
        }
    }

    private void onJoinGame(WsMessageContext context) throws Exception {
        OnJoinGameParams params = context.messageAsClass(OnJoinGameParams.class);
        if (!tokenService.isValidToken(params.userId, params.token)) {
            context.send("{\"error\": \"Token invalide\"}");
            return;
        }
        sessionToUser.put(context.sessionId, params.userId);
        gameService.addSession(params.userId);
        userToContext.put(params.userId, context);
    }

    private void onLeaveGame(WsMessageContext context) throws Exception {
        Integer userId = sessionToUser.get(context.sessionId);
        if (userId == null) {
            context.send("{\"error\": \"Non authentifié\"}");
            return;
        }
        tokenService.removeToken(userId);
        gameService.removeSession(userId);
    }

    private void onClicks(WsMessageContext context) throws Exception {
        Integer userId = sessionToUser.get(context.sessionId);
        if (userId == null) {
            context.send("{\"error\": \"Non authentifié\"}");
            return;
        }
        OnClicksParams params = context.messageAsClass(OnClicksParams.class);
        gameService.addAction(userId,
            new ClickAction(params.clicks)
        );
    }

    private void onPaintPixels(WsMessageContext context) throws Exception {
        Integer userId = sessionToUser.get(context.sessionId);
        if (userId == null) {
            context.send("{\"error\": \"Non authentifié\"}");
            return;
        }
        OnPaintPixelsParams params = context.messageAsClass(OnPaintPixelsParams.class);
        gameService.addAction(userId,
            new PaintPixelsAction(params.pixelsX, params.pixelsY, params.colorHexadecimal)
        );
    }


    private void onUseBonus(WsMessageContext context) throws Exception {
        Integer userId = sessionToUser.get(context.sessionId);
        if (userId == null) {
            context.send("{\"error\": \"Non authentifié\"}");
            return;
        }
        OnUseBonusParams params = context.messageAsClass(OnUseBonusParams.class);
        gameService.useBonus(userId, params.bonusId);
    }

    private Gson gson = new Gson();

	@Override
	public void playerUpdated(int userId, Player player) {
	    WsContext context = userToContext.get(userId);
        String response = String.format("{\"event\": \"player-updated\", \"player\": %s}", gson.toJson(player));
		System.out.println(response);
        context.send(response);
	}

	@Override
	public void pixelsUpdated(Set<Pixel> pixels) {
        String response = String.format("{\"event\": \"pixels-updated\", \"pixels\": %s}", gson.toJson(pixels));
        System.out.println(response);
        for (WsContext context : userToContext.values()) {
            context.send(response);
        }
	}

	@Override
	public void actionFailed(int userId, String message) {
	    WsContext context = userToContext.get(userId);
		String response = String.format("{\"event\": \"action-failed\", \"message\": \"%s\"}", message);
		System.out.println(response);
		context.send(response);
	}

	@Override
	public void skillStarted(int userId, SkillState skill) {
        WsContext context = userToContext.get(userId);
        String response = String.format("{\"event\": \"bonus-started\", \"bonusId\": %d}", skill.getBonusId());
        System.out.println(response);
        context.send(response);
	}

	@Override
	public void skillEnded(int userId, SkillState skill) {
	    WsContext context = userToContext.get(userId);
		String response = String.format("{\"event\": \"bonus-ended\", \"bonusId\": %d}", skill.getBonusId());
        System.out.println(response);
        context.send(response);
	}
}
