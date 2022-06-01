package adminservlet;

import adminmodel.Admin;
import connection.Router;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

class AdminDao {
private Connection conn;

    public AdminDao(Connection conn) {
        this.conn=conn;
    }

    public Admin login(String email, String pwd) {
        try{
            String query = "SELECT * FROM admin WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, pwd);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                Admin admin=new Admin(email,pwd);
                admin.setId(admin.getId());
                return admin;
            }else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    
}
