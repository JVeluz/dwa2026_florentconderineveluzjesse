package m1.dwa.cv.game.actions;

import java.util.HashSet;
import java.util.Set;

import m1.dwa.cv.entities.GameState;
import m1.dwa.cv.entities.Pixel;
import m1.dwa.cv.entities.Player;
import m1.dwa.cv.game.GameConfig;
import m1.dwa.cv.game.events.GameEventSource;

public class PaintPixelsAction implements Action {
    private int[] pixelsX;
    private int[] pixelsY;
    private String colorHexadecimal;

    public PaintPixelsAction(int[] pixelsX, int[] pixelsY, String colorHexadecimal) {
        this.pixelsX = pixelsX;
        this.pixelsY = pixelsY;
        this.colorHexadecimal = colorHexadecimal;
    }

    @Override
    public void execute(GameState state, GameEventSource event, Player player) {
        int userId = player.getUserId();

        Set<Pixel> pixels = new HashSet<>();
        for (int i = 0; i < pixelsX.length; i++) {
            pixels.add(state.getPixel(pixelsX[i], pixelsY[i]));
        }

        int totalPrice = 0;
        for (Pixel pixel : pixels) {
            int ownerId = pixel.getOwnerId();
            if (ownerId == userId)
                continue;
            totalPrice += pixel.getPrice();
        }

        boolean canAffordPixel = player.getCredits() >= totalPrice;
        if (!canAffordPixel) {
            event.actionFailed(userId, "Pas assez de crédits");
            return;
        }

        player.removeCredits(totalPrice);

        Set<Pixel> updatedPixels = new HashSet<>();
        for (Pixel pixel : pixels) {
            int ownerKey = pixel.getOwnerId();
            if (ownerKey == userId && pixel.getColorHexadecimal() == colorHexadecimal)
                continue;
            pixel.setOwnerId(userId);
            pixel.setPrice(pixel.getPrice() + GameConfig.PIXEL_PRICE_INCREASE_ON_PURCHASE);
            pixel.setColorHexadecimal(colorHexadecimal);
            updatedPixels.add(pixel);
        }

        event.playerUpdated(userId, player);
        event.pixelsUpdated(updatedPixels);
    }
}
