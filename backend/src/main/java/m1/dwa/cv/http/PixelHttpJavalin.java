package m1.dwa.cv.http;

import io.javalin.http.Context;
import m1.dwa.cv.daos.PixelDao;

public class PixelHttpJavalin {
    private PixelDao pixelDao;

    public PixelHttpJavalin(PixelDao pixelDao) {
        this.pixelDao = pixelDao;
    }

    public void getPixels(Context context) {
        context.json(pixelDao.every());
    }
}
