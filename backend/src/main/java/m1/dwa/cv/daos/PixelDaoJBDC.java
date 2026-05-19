package m1.dwa.cv.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import m1.dwa.cv.entities.Pixel;

public class PixelDaoJBDC implements PixelDao {

    private final Connection connection = AccesBDD.connexionSGBD();

	@Override
	public Pixel[] every() {
	    List<Pixel> pixels = new ArrayList<>();
        try {
            Statement request = connection.createStatement();
            ResultSet result = request.executeQuery("SELECT * FROM PIXEL");
            while (result.next()) {
                pixels.add(map(result));
            }
            return pixels.toArray(new Pixel[0]);
        }
        catch (SQLException e) {
            System.getLogger(UserDaoJDBC.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);
            return null;
        }
	}

	@Override
	public Pixel update(Pixel pixel) {
	    try {
            connection.setAutoCommit(false);
            PreparedStatement request = connection.prepareStatement("UPDATE PIXEL SET price=?, color_hexadecimal=?, oldness=?, owner_id=? WHERE id=?");
            request.setInt(1, pixel.getPrice());
            request.setString(2, pixel.getColorHexadecimal());
            request.setInt(3, pixel.getOldness());
            request.setInt(4, pixel.getOwnerId());
            request.setInt(5, pixel.getId());

            request.executeUpdate();

            connection.commit();

            return pixel;
        }
        catch (Exception e) {
            try {
                connection.rollback();
            }
            catch (SQLException sqlEx) {
                System.err.println("Pb avec rollback : " + sqlEx);
            }
            return null;
        }
        finally {
            try {
                connection.setAutoCommit(true);
            }
            catch (SQLException ex) {
                System.err.println("Pb avec auto commit : " + ex);
            }
        }
	}

	@Override
	public Pixel findByCoordinates(int x, int y) {
        try {
            Statement request = connection.createStatement();
            ResultSet result = request.executeQuery("SELECT * FROM PIXEL WHERE x = '" + x + "' and y = '" + y + "'");
            if (result.next()) {
                return map(result);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}

	private Pixel map(ResultSet result) throws SQLException {
        Pixel pixel = new Pixel(result.getInt("x"), result.getInt("y"), result.getInt("price"));
        pixel.setId(result.getInt("id"));
        pixel.setColorHexadecimal(result.getString("color_hexadecimal"));
        pixel.setOldness(result.getInt("oldness"));
        pixel.setOwnerId(result.getInt("owner_id"));
        return pixel;
	}
}
