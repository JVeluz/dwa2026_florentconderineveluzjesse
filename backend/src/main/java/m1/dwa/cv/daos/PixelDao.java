package m1.dwa.cv.daos;

import m1.dwa.cv.entities.Pixel;

public interface PixelDao {
	public Pixel[] every();
	public Pixel update(Pixel pixel);
	public Pixel findByCoordinates(int x, int y);
}
