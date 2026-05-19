package m1.dwa.cv.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import m1.dwa.cv.entities.SkillState;

public class SkillDaoJBDC implements SkillDao {

    private final Connection connection = AccesBDD.connexionSGBD();

	@Override
	public SkillState create(SkillState skill) {
    	try {
            connection.setAutoCommit(false);

            PreparedStatement request = connection.prepareStatement("INSERT INTO SKILL_STATE (bonus_id, current_tick user_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            request.setInt(1, skill.getBonusId());
            request.setInt(2, skill.getCurrentTick());
            request.setInt(3, skill.getUserId());

            request.executeUpdate();

            ResultSet id = request.getGeneratedKeys();
            if (id.next()) {
                skill.setId(id.getInt(1));
            }
            connection.commit();

            return skill;
        }
        catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException sqlEx) {
                System.err.println("Pb avec rollback : " + sqlEx);
            }
            return null;
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Pb avec auto commit : " + ex);
            }
        }
	}

	@Override
	public SkillState delete(SkillState skill) {
	    try {
            Statement request = connection.createStatement();
            request.executeQuery("DELETE FROM TABLE SKILL_STATE WHERE id = '" + skill.getId() + "'");
            return skill;
        }
        catch (SQLException e) {
            System.getLogger(SkillDaoJBDC.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);
            return null;
        }
	}

	@Override
	public SkillState[] every() {
        List<SkillState> skills = new ArrayList<>();
        try {
            Statement request = connection.createStatement();
            ResultSet result = request.executeQuery("SELECT * FROM SKILL_STATE");
            while (result.next()) {
                skills.add(map(result));
            }
            return skills.toArray(new SkillState[0]);
        }
        catch (SQLException e) {
            System.getLogger(SkillDaoJBDC.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);
            return null;
        }
	}

	@Override
	public SkillState update(SkillState skill) {
       	try {
            connection.setAutoCommit(false);

            PreparedStatement request = connection.prepareStatement("UPDATE SKILL_STATE SET bonus_id=?, current_tick=? WHERE id=?");
            request.setInt(1, skill.getBonusId());
            request.setInt(2, skill.getCurrentTick());

            request.executeUpdate();

            connection.commit();

            return skill;
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

	private SkillState map(ResultSet result) throws SQLException {
	    SkillState skill = new SkillState(result.getInt("bonus_id"), result.getInt("current_tick"));
		skill.setId(result.getInt("id"));
		skill.setUserId(result.getInt("user_id"));
		return skill;
	}
}
