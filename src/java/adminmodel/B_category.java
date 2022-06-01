/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminmodel;

/**
 *
 * @author Ugochukwu123
 */
public class B_category {
    private int id;
    private String b_name;
    private String m_name;
    private String email;
    private String pwd;
    private String u_service;
    private String price;
    private String state;
    private String b_location;
    private String l_g_a;
    private String address;

    public B_category() {
    }

    public B_category(String b_name, String m_name, String email, String pwd, String u_service, String price, String state, String b_location, String l_g_a, String address) {
        this.b_name = b_name;
        this.m_name = m_name;
        this.email = email;
        this.pwd = pwd;
        this.u_service = u_service;
        this.price = price;
        this.state = state;
        this.b_location = b_location;
        this.l_g_a = l_g_a;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getU_service() {
        return u_service;
    }

    public void setU_service(String u_service) {
        this.u_service = u_service;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getB_location() {
        return b_location;
    }

    public void setB_location(String b_location) {
        this.b_location = b_location;
    }

    public String getL_g_a() {
        return l_g_a;
    }

    public void setL_g_a(String l_g_a) {
        this.l_g_a = l_g_a;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "identity{" + "id=" + id + ", b_name=" + b_name + ", m_name=" + m_name + ", email=" + email + ", pwd=" + pwd + ", u_service=" + u_service + ", price=" + price + ", state=" + state + ", b_location=" + b_location + ", l_g_a=" + l_g_a + ", address=" + address + '}';
    }
    
    
}
