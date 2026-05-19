
package m1.dwa.cv.daos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import m1.dwa.cv.entities.User;

public class UserDaoJDBC implements UserDao{

    public UserDaoJDBC() {
    }

    private final Connection conn = AccesBDD.connexionSGBD();

    @Override
    public User find(int id){
        try {
            Statement req = conn.createStatement();
            ResultSet res = req.executeQuery("SELECT * FROM user WHERE id = " + id);
            boolean trouve = res.next();
            if(trouve){
                return(map(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(null);
    }

    @Override
    public User create(User user){
        try {
            conn.setAutoCommit(false);

            PreparedStatement req = conn.prepareStatement("INSERT INTO USER (pseudo, password, age, country, credits) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            req.setString(1, user.getPseudo());
            req.setString(2, user.getPassword());
            req.setInt(3, user.getAge());
            req.setString(4, user.getCountryCode());
            req.setInt(5, user.getCredits());

            req.executeUpdate();

            ResultSet id = req.getGeneratedKeys();
            if (id.next()) {
                user.setId(id.getInt(1));
            }
            conn.commit();

            return(user);
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException sqlEx) {
                System.err.println("Pb avec rollback : " + sqlEx);
                
            }
            return(null);
        } finally{
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Pb avec auto commit : " + ex);
            }
            
        }
        
    }

    @Override
    public User update(User user){
        try {

            conn.setAutoCommit(false);
            PreparedStatement req = conn.prepareStatement("UPDATE USER SET pseudo=?, password=?, age=?, country=?, credits=? WHERE id=?");
            req.setString(1, user.getPseudo());
            req.setString(2, user.getPassword());
            req.setInt(3, user.getAge());
            req.setString(4, user.getCountryCode());
            req.setInt(5, user.getCredits());
            req.setInt(6, user.getId());

            req.executeUpdate();

            conn.commit();

            return(user);
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException sqlEx) {
                System.err.println("Pb avec rollback : " + sqlEx);
                
            }
            return(null);
        } finally{
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Pb avec auto commit : " + ex);
            }
            
        }
    }

    @Override 
    public User[] every(){
        List<User> users = new ArrayList<>();

        try {
            Statement req = conn.createStatement();
            ResultSet res = req.executeQuery("SELECT * FROM user");
            while(res.next()){
                users.add(map(res));
            }
            return(users.toArray(new User[0]));
        } catch (SQLException ex) {
            System.getLogger(UserDaoJDBC.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return(null);
        }
    }
    
    @Override
    public User findByPseudo(String pseudo){
        try {
            Statement req = conn.createStatement();
            ResultSet res = req.executeQuery("SELECT * FROM user WHERE pseudo = '" + pseudo + "'");
            boolean trouve = res.next();
            if(trouve){
                return(map(res));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return(null);
    }


    
    private User map(ResultSet res) throws SQLException {
        User user = new User();

        user.setId(res.getInt("id"));
        user.setPseudo(res.getString("pseudo"));
        user.setPassword(res.getString("password"));
        user.setAge(res.getInt("age"));
        user.setCountryCode(res.getString("country"));
        user.setCredits(res.getInt("credits"));

        return user;
    }

}

/*
try {

    conn.setAutoCommit(false);
    //Tu fous ta requete ici
    conn.commit();

    return(user);
} catch (Exception e) {
    try {
        conn.rollback();
        //si la transaction echoue ça rollback
    } catch (SQLException sqlEx) {
        System.err.println("Pb avec rollback : " + sqlEx);
        
    }
    return(null);
} finally{
    try {
        //on repasse à autocommit à la fin de la requete
        conn.setAutoCommit(true);
    } catch (SQLException ex) {
        System.err.println("Pb avec auto commit : " + ex);
    }
            
}
*/