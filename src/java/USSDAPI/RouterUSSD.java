package USSDAPI;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import connection.Router;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("generic")
public class RouterUSSD {
    private static final String CALL_REQUEST_TYPE = "Call";

    @Context
    private UriInfo context;

    public RouterUSSD() {
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public void getXml() {
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @POST
    @Path("/order")
    @Produces(MediaType.TEXT_PLAIN)
    public String rec(@FormParam("input") String in){
        
        String response = "";
        try{
            String []newIn = in.replace("*", "_").split("_");
            response = "";
            int lIn = newIn.length;
            String biznCode;
            if(in.isEmpty()){
                response = "Welcome to BookHere. \n"
                        + "Please enter business code to proceed:";
                return response;
            }
            switch(lIn){
                case 1:
                    biznCode = newIn[0];
                    try{
                        int bizCode = Integer.parseInt(biznCode);
                        ResultSet rs = getResultSetReg(bizCode);
                        try {
                            if (rs.next()){
                                String bizName = rs.getString(2);
                                response = "Welcome to " + bizName + "\n"
                                        + "1. Place an order\n"
                                        + "2. Drop a complain\n"
                                        + "3. Speak to our customer care representative";
                            }else{
                                response = "Please enter a valid business id";
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(RouterUSSD.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }catch(NumberFormatException e){
                        response = "Please enter a valid business id";
                    }
                    break;
                case 2:
                    biznCode = newIn[0];
                    int bizCode = Integer.parseInt(biznCode);
                    try {
                        if (getResultSetReg(bizCode).next()){
                            if(newIn[1].equals("1")){
                                ResultSet rs = getResultSetProd(bizCode);
                                if(rs.next()){
                                    int count = 1;
                                    do{
                                        String prodName = rs.getString(2);
                                        response += count++ + ". " + prodName + "\n";
                                    }while(rs.next());
                                }else{
                                    response ="Seller has run out of stock";
                                }
                            }else if(newIn[1].equals("2")){
                                response = "1. Incomplete Items Delivered\n2. Inferior Quality\n3. Expired Items\n"
                                        + "4. Wrong Item(s) Delivered";
                            }else if(newIn[1].equals("3")){
                                String query = "INSERT INTO customer_requests (request_type, details, sellers_id) VALUES (?,?,?)";
                                Connection conn = new Router().dbConnect();
                                PreparedStatement stmt = conn.prepareStatement(query);
                                stmt.setString(1, CALL_REQUEST_TYPE);
                                stmt.setString(2, "Call");
                                stmt.setInt(3, bizCode);
                                stmt.executeUpdate();
                                response = "We'll call you shortly";
                            }else{
                                response = "Please enter a valid response";
                            }
                        }else{
                            response = "Please enter a valid business id";
                            //todo: take us back to case 1;
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(RouterUSSD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 3:
                    biznCode = newIn[0];
                    bizCode = Integer.parseInt(biznCode);
                    
                    if (getResultSetReg(bizCode).next()){
                        if(newIn[1].equals("1")){
                            ResultSet rs = getResultSetProd(bizCode);
                            String product_no_ussd = newIn[2];
                            int prod_no_ussd = Integer.parseInt(product_no_ussd);
                            while (rs.next()){
                                if (rs.getRow() == prod_no_ussd){
                                    double price = rs.getDouble(4);
                                    String name = rs.getString(2);
                                    response = "The cost of " + name + " is " + "N" + price + "\nEnter quantity";
                                }
                            }
                            if (response.equals("")) response = "Please enter a valid input";
                        }else if(newIn[1].equals("2")){
                            String comp_no = newIn[2];
                            int complaint_no = Integer.parseInt(comp_no);
                            response = sendComplaintToDB(complaint_no, bizCode);
                        }
                    }
                    break;
                case 4:
                    biznCode = newIn[0];
                    bizCode = Integer.parseInt(biznCode);
                    if (getResultSetReg(bizCode).next()){
                        if(newIn[1].equals("1")){
                            ResultSet rs = getResultSetProd(bizCode);
                            String product_no_ussd = newIn[2];
                            int prod_no_ussd = Integer.parseInt(product_no_ussd);
                            while (rs.next()){
                                if (rs.getRow() == prod_no_ussd){
                                    double price = rs.getDouble(4);
                                    String name = rs.getString(2);
                                    String quant = newIn[3];
                                    int quantity = Integer.parseInt(quant);
                                    
                                    if(quantity <= 0){
                                        response = "Enter a valid quantity greater than 1";
                                    }else{
                                        response = "Are you sure you want to buy " + quantity + " of "
                                            + name + " for N" + price*quantity +"?\n1. Yes\n2. No"; 
                                    }
                                }
                            }
                            if (response.equals("")) response = "Please enter a valid input";
                        }
                    }
                    break;
                case 5:
                    biznCode = newIn[0];
                    bizCode = Integer.parseInt(biznCode);
                    if (getResultSetReg(bizCode).next()){
                        if(newIn[1].equals("1")){
                            ResultSet rs = getResultSetProd(bizCode);
                            String product_no_ussd = newIn[2];
                            int prod_no_ussd = Integer.parseInt(product_no_ussd);
                            while (rs.next()){
                                if (rs.getRow() == prod_no_ussd){
                                    double price = rs.getDouble(4);
                                    String name = rs.getString(2);
                                    String quant = newIn[3];
                                    int quantity = Integer.parseInt(quant);
                                    
                                    String fin_resp = newIn[4];
                                    int final_resp = Integer.parseInt(fin_resp);
                                    if (final_resp == 1){
                                        try {
                                            Connection conn = new Router().dbConnect();
                                            String query = "INSERT INTO orders (product_name, quantity, total_amount, sellers_id) VALUES (?,?,?,?)";
                                            PreparedStatement stmt = conn.prepareStatement(query);
                                            stmt.setString(1, name);
                                            stmt.setInt(2, quantity);
                                            stmt.setDouble(3, price*quantity);
                                            stmt.setInt(4, bizCode);
                                            stmt.executeUpdate();
                                            response = "Thanks for your order\nYou will be receiving your items soon!!!";
                                        
                                        } catch (ClassNotFoundException ex) {
                                            Logger.getLogger(RouterUSSD.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        
                                    }else if(final_resp == 2){
                                        response = "Thanks for choosing BookHere\nWe hope to hear from you again";
                                    }else{
                                        response = "Please enter a valid input";
                                    }
                                    
                                }
                            }
                        }
                    }
                    break;
                default:
                    response = "Enyia! Shie eba puo";
                    break;
            }
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return response;
    }
    
//    public String rec2(@FormParam("input") String in){
//        
//    }
//    
    private ResultSet getResultSetReg(int bizCode) {
        try {
            Connection conn = new Router().dbConnect();
            String query = "SELECT * FROM registration WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bizCode);
            ResultSet rs = stmt.executeQuery();
            
            return rs;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RouterUSSD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private ResultSet getResultSetProd(int bizCode) {
        try {
            Connection conn = new Router().dbConnect();
            String query = "SELECT * FROM products WHERE sellers_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bizCode);
            ResultSet rs = stmt.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(RouterUSSD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RouterUSSD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String sendComplaintToDB(int complaint_no, int sellers_id) {
        
        try {
            Connection conn = new Router().dbConnect();
            String query = "INSERT INTO customer_requests (request_type, details, sellers_id) VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "Complaint");
        
            switch (complaint_no){
                case 1:
                    stmt.setString(2,"Incomplete Items Delivered");
                    stmt.setInt(3, sellers_id);
                    stmt.executeUpdate();
                    break;
                case 2:
                    stmt.setString(2,"Inferior Quality");
                    stmt.setInt(3, sellers_id);
                    stmt.executeUpdate();
                    break;
                case 3:
                    stmt.setString(2,"Expired Items");
                    stmt.setInt(3, sellers_id);
                    stmt.executeUpdate();
                    break;
                case 4:
                    stmt.setString(2,"Wrong Item(s) Delivered");
                    stmt.setInt(3, sellers_id);
                    stmt.executeUpdate();
                    break;
                default:
                    return "Please enter a valid input";
            }    
        } catch (SQLException ex) {
            Logger.getLogger(RouterUSSD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RouterUSSD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Your Complaint have been received\nWe'll get back to you shortly";
    }
}