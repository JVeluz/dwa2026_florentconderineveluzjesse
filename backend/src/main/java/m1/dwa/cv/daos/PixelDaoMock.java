package m1.dwa.cv.daos;

import java.util.HashMap;
import java.util.Map;

import m1.dwa.cv.entities.Pixel;
import m1.dwa.cv.game.GameConfig;

public class PixelDaoMock implements PixelDao {
    private final static int GRID_SIZE = 50;
    private Map<Integer, Pixel> storage = new HashMap<>();

    public PixelDaoMock() {
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                Pixel pixel = new Pixel(x, y, 5);
                int key = getKey(x, y);
                pixel.setId(key);
                pixel.setOwnerId(-1);
                pixel.setPrice(GameConfig.DEFAULT_PIXEL_PRICE);
                storage.put(key, pixel);
            }
        }
    }

    @Override
    public Pixel[] every() {
        return storage.values().toArray(new Pixel[0]);
    }

    @Override
	public Pixel update(Pixel pixel) {
        return storage.put(getKey(pixel.getX(), pixel.getY()), pixel);
    }

    @Override
    public Pixel findByCoordinates(int x, int y) {
        return storage.get(getKey(x, y));
    }

    private int getKey(int x, int y) {
        return y * GRID_SIZE + x;
    }
}
