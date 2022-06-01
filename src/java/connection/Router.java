package connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import usermodel.User;

/**
 *
 * @author Ugochukwu123
 */
@WebServlet(name = "Router", urlPatterns = {"/Router"})
public class Router extends HttpServlet{
    PrintWriter out;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            
        out=response.getWriter();
        String url = "/index.html";
        String origin = request.getParameter("origin");
        switch (origin){
            case "client_login":
                String username = request.getParameter("email");
                String password = request.getParameter("pwd");
                if(login(username,password) != null){
                    User user = login(username,password);
                    HttpSession session=request.getSession();
                    session.setAttribute("loggedUser", user);
                    url = "/clientdashboard.jsp"; 
                }                
                break;
                
            case "client_register":
                String query1="INSERT INTO `registration` (`b_name`, `m_name`, `email`, `pwd`, `u_service`, `state`, `l_g_a`, `address`)  VALUES (?,?,?,?,?,?,?,?)";
                
                String b_name = request.getParameter("business_name");
                String m_name = request.getParameter("manager_name");
                String email = request.getParameter("email");
                String pwd = request.getParameter("password");
                String pwd2 = request.getParameter("password2");
                String u_service = request.getParameter("service");
                String state = request.getParameter("state");
                String l_g_a = request.getParameter("lga");
                String address = request.getParameter("address");
                out.println(9999);
                
//                if (pwd != pwd2) response.sendRedirect(url);
                if (login(email,pwd) == null){
                    try {
                        Connection conn=dbConnect();
                        PreparedStatement stmt1 = conn.prepareStatement(query1);
                        stmt1.setString(1, b_name);
                        stmt1.setString(2, m_name);
                        stmt1.setString(3, email);
                        stmt1.setString(4, pwd);
                        stmt1.setString(5, u_service);
                        stmt1.setString(6, state);
                        stmt1.setString(7, l_g_a);
                        stmt1.setString(8, address);
                        stmt1.executeUpdate();
                        url="/clientLogin.jsp";
                        
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "add_new_product":
                String query="INSERT INTO `products` (`name`, `description`, `price`,`sellers_id`)  VALUES (?,?,?,?)";
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                Double price = Double.parseDouble(request.getParameter("price"));
                HttpSession session = request.getSession();
                User user = (User)session.getAttribute("loggedUser");
                
                try{
                    Connection conn=dbConnect();
                    PreparedStatement stmt2 = conn.prepareStatement(query);
                    stmt2.setString(1, name);
                    stmt2.setString(2, description);
                    stmt2.setDouble(3, price);
                    stmt2.setInt(4, user.getId());
                    stmt2.executeUpdate();
                    url = "/user_products.jsp";
                }catch(SQLException | ClassNotFoundException e){
                    Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, e);
                }
                break;
            default:
                break;
    
        }
        response.sendRedirect(url);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String origin = req.getParameter("origin");
        switch(origin){
            case "delete_product":
                int id= Integer.parseInt(req.getParameter("id"));
                try{
                    String query = "DELETE FROM `products` WHERE id = ?";
                    Connection conn=dbConnect();
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                    resp.sendRedirect("/user_products.jsp");
                }catch(SQLException | ClassNotFoundException e){
                    e.printStackTrace();
                }
                break;
            case "delete_product_admin":
                int id3= Integer.parseInt(req.getParameter("id"));
                try{
                    String query = "DELETE FROM `products` WHERE id = ?";
                    Connection conn=dbConnect();
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setInt(1, id3);
                    stmt.executeUpdate();
                    resp.sendRedirect("/adminuserproducts.jsp");
                }catch(SQLException | ClassNotFoundException e){
                    e.printStackTrace();
                }
                break;
            case "edit_product":
                int id4= Integer.parseInt(req.getParameter("id"));
                    
                    resp.sendRedirect("/newproduct.jsp");
                break;
            case "deliver_product":
                int id1 = Integer.parseInt(req.getParameter("id"));
                try{
                    String query = "UPDATE orders SET status = ? WHERE id = ?";
                    Connection conn=dbConnect();
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, "Delivered");
                    stmt.setInt(2, id1);
                    stmt.executeUpdate();
                    resp.sendRedirect("/clientdashboard.jsp");
                }catch(SQLException | ClassNotFoundException e){
                    e.printStackTrace();
                }
                break;
            case "delete_client":
                int id2= Integer.parseInt(req.getParameter("id"));
                try{
                    String query1 = "DELETE FROM registration WHERE id = ?";
                    String query2 = "DELETE FROM products WHERE sellers_id = ?";
                    String query3 = "DELETE FROM orders WHERE sellers_id = ?";
                    String query4 = "DELETE FROM customer_requests WHERE sellers_id = ?";
                    
                    Connection conn=dbConnect();
                    PreparedStatement stmt1 = conn.prepareStatement(query1);
                    PreparedStatement stmt2 = conn.prepareStatement(query2);
                    PreparedStatement stmt3 = conn.prepareStatement(query3);
                    PreparedStatement stmt4 = conn.prepareStatement(query4);
                    stmt1.setInt(1, id2);
                    stmt2.setInt(1, id2);
                    stmt3.setInt(1, id2);
                    stmt4.setInt(1, id2);
                    stmt1.executeUpdate();
                    stmt2.executeUpdate();
                    stmt3.executeUpdate();
                    stmt4.executeUpdate();
                    resp.sendRedirect("/Admindashboard.jsp");
                }catch(SQLException | ClassNotFoundException e){
                    e.printStackTrace();
                }
                break;
            case "client_logout":
                HttpSession session = req.getSession();
                session.removeAttribute("loggedUser");
                resp.sendRedirect("clientLogin.jsp");
                break;
            default:
                break;
        }
        
    }
    
    
   public Connection dbConnect() throws SQLException, ClassNotFoundException{
        String user = "root";
        String password = "root";
        String url;
        url = "jdbc:mysql://localhost:3306/bookhere";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
   }

    private User login(String username, String password) {
        try(Connection conn = dbConnect()){
            String query = "SELECT * FROM registration WHERE email = ? AND pwd = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(3));
                user.setEmail(username);
                user.setPwd(password);
                return user;
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}