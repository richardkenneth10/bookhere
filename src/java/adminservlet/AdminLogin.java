


package adminservlet;

import adminmodel.Admin;
import connection.Router;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ugochukwu123
 */
public class AdminLogin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("email");
            String pwd = request.getParameter("pwd");
            
            Router router=new Router();
            AdminDao dao = new AdminDao(router.dbConnect());
            Admin admin = dao.login(email, pwd);
            admin.setId(admin.getId()+1);
            if(admin!=null){
                HttpSession session=request.getSession();
                session.setAttribute("loggedAdmin",admin);
                response.sendRedirect("/Admindashboard.jsp");
            }else{
                out.println("user doesn't exist");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}