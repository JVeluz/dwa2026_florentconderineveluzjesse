package m1.dwa.cv.services;

import m1.dwa.cv.daos.PixelDao;
import m1.dwa.cv.entities.Pixel;

public class PixelService {
    private PixelDao pixelDao;

    public PixelService(PixelDao pixelDao) {
        this.pixelDao = pixelDao;
    }

    public Pixel[] every() {
        return pixelDao.every();
    }
}
