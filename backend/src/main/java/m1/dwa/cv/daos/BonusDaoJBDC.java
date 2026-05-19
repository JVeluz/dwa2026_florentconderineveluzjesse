package m1.dwa.cv.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import m1.dwa.cv.entities.Bonus;

public class BonusDaoJBDC implements BonusDao {

    private final Connection connection = AccesBDD.connexionSGBD();

	@Override
	public Bonus find(int id) {
    	try {
            Statement request = connection.createStatement();
            ResultSet result = request.executeQuery("SELECT * FROM BONUS WHERE id = " + id);
            if (result.next()) {
                return map(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}

	@Override
	public Bonus[] every() {
        List<Bonus> bonuses = new ArrayList<>();
        try {
            Statement request = connection.createStatement();
            ResultSet result = request.executeQuery("SELECT * FROM BONUS");
            while (result.next()) {
                bonuses.add(map(result));
            }
            return bonuses.toArray(new Bonus[0]);
        } catch (SQLException ex) {
            System.getLogger(PixelDaoJBDC.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return null;
        }
	}

	private Bonus map(ResultSet result) throws SQLException {
        Bonus bonus = new Bonus();
        bonus.setId(result.getInt("id"));
        bonus.setName(result.getString("name"));
        bonus.setDescription(result.getString("description"));
        bonus.setPrice(result.getInt("price"));
        return bonus;
	}
}
