package ca.jrvs.apps.jdbc;

import java.sql.*;
import java.util.List;

public class JDBCExecutor {
    public static void main(String[] args) {
        System.out.println("hello learning JDBC");
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport","postgres","password");
        try {
            Connection connection = dcm.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = new Customer();
            customer.setFirstName("John");
            customer.setLastName("Adams");
            customer.setEmail("jadams.wh.gov");
            customer.setAddress("1223 that st");
            customer.setCity("ny");
            customer.setPhone("23143531");
            customer.setZipCode("f4fj42");

            Customer dbCostumer = customerDAO.create(customer);
            System.out.println(dbCostumer);
            dbCostumer = customerDAO.findById(dbCostumer.getId());
            System.out.println(dbCostumer);
            dbCostumer.setEmail("john@gmail.com");
            dbCostumer = customerDAO.update(dbCostumer);
            System.out.println(dbCostumer);
            customerDAO.delete(dbCostumer.getId());

            OrderDAO orderDAO = new OrderDAO(connection);
            Order order = orderDAO.findById(1000);
            System.out.println(order);
            List<Order> orders = orderDAO.getOrdersForCostumer(789);


        }

            catch(SQLException e){
                e.printStackTrace();

            }
        }
    }

