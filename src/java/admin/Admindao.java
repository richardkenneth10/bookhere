package admin;

import Adminservice.AdminService;
import adminmodel.Admin;
import java.sql.*;
public class Admindao implements AdminService {
    private final Connection con;
    private String query;
    private PreparedStatement pst;
    private ResultSet rs;

    public Admindao(Connection con) {
        this.con = con;
    }
    @Override
    public Admin logAdmin(String email, String pwd){
    Admin admin = null;
    try{
    query ="select from admin where email=? amd pwd?";
    pst = this.con.prepareStatement(query);
    pst.setString(1, email);
    pst.setString(2, pwd);
    rs = pst.executeQuery();
    if(rs.next()){
        admin = new Admin();
        admin.setId(rs.getInt("id"));
        admin.setEmail(rs.getString("email"));
        admin.setPwd(rs.getString("pwd"));
        
    
    }
    }catch(Exception ex){
        ex.printStackTrace();
    }
    
    
    
    
    return admin;
    }
}
