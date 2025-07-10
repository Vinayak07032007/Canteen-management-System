package com.vinayak;

import java.sql.*;
import java.util.*;


class DBConnection
 {
    private static final String URL="jdbc:postgresql://localhost:5432/canteen";
    private static final String USER="postgres";
    private static final String PASSWORD="2007";

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}


class MenuItem 
{
    private String fooditem;
    private double price;
    private int stock;

    public MenuItem(String fooditem,double price,int stock) 
    {
        this.fooditem = fooditem;
        this.price = price;
        this.stock = stock;
    }

    public String getFooditem()
    {
        return fooditem;
    }

    public double getPrice()
    {
        return price;
    }

    public int getStock()
    {
        return stock;
    }

    public static void addMenuItem(String name,double price,int stock)
    {
        try (Connection conn=DBConnection.getConnection())
        {
            String sql="INSERT INTO menus(fooditem,price,stock)VALUES(?, ?, ?)";
            PreparedStatement stmt=conn.prepareStatement(sql);
            stmt.setString(1,name);
            stmt.setDouble(2,price);
            stmt.setInt(3,stock);
            stmt.executeUpdate();
            System.out.println(" Menu item added: "+name);
        } 
        catch (SQLException e)
        {
            System.out.println(" Error occurs while adding the menu item: " +e.getMessage());
        }
    }

    public static void listMenuItems()
    {
        try (Connection conn = DBConnection.getConnection()) 
        {
            String sql = "SELECT * FROM menus";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.isBeforeFirst()) 
            {
                System.out.println("No items in menu.");
                return;
            }

            System.out.println("\n--- MENU ---");
            while (rs.next())
            {
                System.out.println("Item: " + rs.getString("fooditem"));
                System.out.println("Price: " + rs.getDouble("price"));
                System.out.println("Stock: " + rs.getInt("stock"));
            }
        } 
        catch (Exception e)
        {
            System.out.println(" Error occurs while viewing the menu item: "+e.getMessage());
        }
    }

    public static boolean checkMenuItemExists(String item)
    {
        try (Connection conn = DBConnection.getConnection())
        {
            String sql = "SELECT 1 FROM menus WHERE fooditem = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,item);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } 
        catch (Exception e) 
        {
            System.out.println("Error occurs while checking the menu item:"+e.getMessage());
            return false;
        }
    }
}


class Order
{
    public static void placeOrder(String studentName,String itemName,int quantity)
     {
        try (Connection conn = DBConnection.getConnection())
         {
            String sql = "SELECT stock, price FROM menus WHERE fooditem = ?";
            PreparedStatement checkStmt = conn.prepareStatement(sql);
            checkStmt.setString(1,itemName);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) 
            {
                int stock = rs.getInt("stock");
                double price = rs.getDouble("price");

                if (stock >= quantity)
                {
                   
                    String orderSql = "INSERT INTO foodorders (student_name,item_name,quantity,total_price) VALUES (?, ?, ?, ?)";
                    PreparedStatement is = conn.prepareStatement(orderSql);
                    is.setString(1,studentName);
                    is.setString(2,itemName);
                    is.setInt(3,quantity);
                    is.setDouble(4,price * quantity);
                    is.executeUpdate();

                    
                    String updateSql = "UPDATE menus SET stock = stock - ? WHERE fooditem = ?";
                    PreparedStatement us = conn.prepareStatement(updateSql);
                    us.setInt(1,quantity);
                    us.setString(2,itemName);
                    us.executeUpdate();

                    System.out.println("Order placed: " + quantity + "x " + itemName + "for " + studentName);
                } 
                else
                {
                    System.out.println("Not enough stock for: " +itemName);
                }
            } 
            else
            {
                System.out.println("Menu item not found.");
            }
        } 
        catch (Exception e)
        {
            System.out.println("Error occurs while placing the order: "+e.getMessage());
        }
    }

    public static void viewOrders()
    {
        try (Connection conn = DBConnection.getConnection())
        {
            String sql = "SELECT * FROM foodorders";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.isBeforeFirst()) 
            {
                System.out.println("No orders placed yet.");
                return;
            }

            System.out.println("\n--- ORDER REPORT ---");
            while (rs.next())
            {
                System.out.println("Student: " + rs.getString("student_name"));
                System.out.println("Item: " + rs.getString("item_name"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
                System.out.println("Total: " + rs.getDouble("total_price"));
            }
        } 
        catch (Exception e)
        {
            System.out.println("Error occurs while viewing the orders: "+e.getMessage());
        }
    }
}


class Task extends Thread
{
    private boolean running = true;

    public void stopTask() 
    {
        running = false;
    }

    public void run() 
    {
        while (running) 
        {
            try 
            {
                Thread.sleep(15000);    
            } 
            catch (InterruptedException e) 
            {
                System.out.println("Thread interrupted.");
            }
        }
    }
}


public class App 
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        Task bgTask = null;
       
        while (true) 
        {
            System.out.println("\n#==== CANTEEN MANAGEMENT ====#");
            System.out.println("1. Add Menu Item");
            System.out.println("2. View Menu");
            System.out.println("3. Place Order");
            System.out.println("4. View Orders");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int c = sc.nextInt();
        
            switch (c) 
            {
                case 1:
                    System.out.println("Enter item name: ");
                    sc.nextLine();
                    String name = sc.nextLine();

                    System.out.print("Enter price: ");
                    double price = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Enter stock: ");
                    int stock = sc.nextInt();
                    sc.nextLine();

                    MenuItem.addMenuItem(name,price,stock);
                    break;

                case 2:
                    MenuItem.listMenuItems();
                    break;

                case 3:
                    System.out.print("Enter name of Student: ");
                    String student = sc.nextLine();

                    System.out.print("Enter food item: ");
                    String item = sc.nextLine();

                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();
                    sc.nextLine();

                    if (MenuItem.checkMenuItemExists(item)) 
                    {
                        Order.placeOrder(student,item,qty);
                    } 
                    else 
                    {
                        System.out.println("Item not found.");
                    }
                    break;

                case 4:
                    Order.viewOrders();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    if (bgTask != null) 
                    bgTask.stopTask();
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice please enter valid number");
            }
        }
    }
}




