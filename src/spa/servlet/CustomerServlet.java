package spa.servlet;

import lk.ijse.jsp.dto.CustomerDTO;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

//http://localhost:8080/pos_one/customer
//http://localhost:8080/pos_one/pages/customer? 404
//http://localhost:8080/customer? 404

//http://localhost:8080/pos_one/pages/customer//
//http:://localhost:8080/pos_one/pages/customer
//http:://localhost:8080/pos_one/pages/customer

@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_pos", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("select * from customer");
            ResultSet rst = pstm.executeQuery();

            ArrayList<CustomerDTO> allCustomers = new ArrayList<>();

          /*  StringBuilder JsonText = new StringBuilder("[");


            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                String tp = rst.getString(4);
                allCustomers.add(new CustomerDTO(id, name, address,tp));
                String cus= "{\"id\":\""+id+"\",\"name\":\""+name+"\",\"address\":\""+address+"\",\"tp\":\""+tp+"\"}";
                JsonText.append(cus).append(",");

            }
            JsonText.deleteCharAt(JsonText.length()-1);
            JsonText.append("]");
*/

            JsonArrayBuilder allCustomer = Json.createArrayBuilder();

            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                String tp = rst.getString(4);

                JsonObjectBuilder customerObject = Json.createObjectBuilder();
                customerObject.add("id", id);
                customerObject.add("name", name);
                customerObject.add("address", address);
                customerObject.add("tp", tp);

                allCustomer.add(customerObject.build());

            }


            /*String data =JsonText.toString();
            resp.setContentType("application/json");
            resp.getWriter().print(data);*/

            resp.setContentType("application/json");
            resp.getWriter().print(allCustomer.build());


        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_pos", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("insert into customer values(?,?,?,?)");
            pstm.setObject(1, cusID);
            pstm.setObject(2, cusName);
            pstm.setObject(3, cusAddress);
            pstm.setObject(4, cusSalary);
            resp.setContentType("application/json");
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            if (pstm.executeUpdate() > 0) {

                objectBuilder.add("Status", "ok");
                objectBuilder.add("message", "Successfully Added..!");
                objectBuilder.add("data", "");

                arrayBuilder.add(objectBuilder.build());
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().print(arrayBuilder.build());


            } else {
                objectBuilder.add("Status", "Error");
                objectBuilder.add("message", "Successfully Added..!");
                objectBuilder.add("data", "");

                arrayBuilder.add(objectBuilder.build());
                resp.setStatus(400);
                resp.setContentType("application/json");
                resp.getWriter().print(arrayBuilder.build());
            }

            resp.getWriter().print(arrayBuilder.build());


        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_pos", "root", "1234");

            PreparedStatement pstm2 = connection.prepareStatement("delete from customer where cus_id=?");
            pstm2.setObject(1, cusID);
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            if (pstm2.executeUpdate() > 0) {

                objectBuilder.add("Status", "ok");
                objectBuilder.add("message", "Successfully Deleted..!");
                objectBuilder.add("data", "");

                arrayBuilder.add(objectBuilder.build());
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().print(arrayBuilder.build());


            } else {
                objectBuilder.add("Status", "Error");
                objectBuilder.add("message", "Delete Failed like  you..!");
                objectBuilder.add("data", "");

                arrayBuilder.add(objectBuilder.build());
                resp.setStatus(400);
                resp.setContentType("application/json");
                resp.getWriter().print(arrayBuilder.build());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_pos", "root", "1234");

            PreparedStatement pstm3 = connection.prepareStatement("update customer set cus_name=?,cus_address=?, cus_salary=? where cus_id=?");
            pstm3.setObject(3, cusID);
            pstm3.setObject(1, cusName);
            pstm3.setObject(2, cusAddress);
            pstm3.setObject(2, cusSalary);

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            if (pstm3.executeUpdate() > 0) {
                objectBuilder.add("Status", "ok");
                objectBuilder.add("message", "Successfully Updated..!");
                objectBuilder.add("data", "");

                arrayBuilder.add(objectBuilder.build());
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().print(arrayBuilder.build());


            } else {
                objectBuilder.add("Status", "Error");
                objectBuilder.add("message", "Failure Like you");
                objectBuilder.add("data", "");

                arrayBuilder.add(objectBuilder.build());
                resp.setStatus(400);
                resp.setContentType("application/json");
                resp.getWriter().print(arrayBuilder.build());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
        }
    }
}
