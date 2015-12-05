package com;

import java.sql.*;
import java.util.ArrayList;

public class Utils 
{
	private Connection connection;
	private PreparedStatement preparedStatement;
	private String query;
	//private Product product;
	private Member member;
	private Inventory inventory;
	private ResultSet resultSet;
	//private ArrayList<Product> productList;
	private ArrayList<Inventory> inventoryList;
		
	public Connection getDataBaseConnection() throws Exception
	{	   
		try
        {			
			Class.forName("com.mysql.jdbc.Driver");
			final String HOST = "jdbc:mysql://localhost";
			final String DATABASE = "onlinebookstore";
			final String URL = HOST+"/"+DATABASE;			
			final String USERNAME = "root";			
			final String PASSWORD = "chetan";
			
			connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }

        catch (Exception ex)
        {
            throw ex;
        }
		return connection;
    }  
    
    /*public ArrayList<Product> getProductList() throws Exception
	{	
		try
		{				
			connection = getDataBaseConnection();
			
			query = "SELECT * from products";
			preparedStatement = connection.prepareStatement(query);
			//preparedStatement.setInt(1,questionID);
			resultSet=preparedStatement.executeQuery();
			
			productList = new ArrayList<Product>();
			
			while(resultSet.next())	
			{
				product = new Product();
										
				product.setProductName(resultSet.getString("product_name"));
				product.setProductImage(resultSet.getString("product_image"));
				product.setPrice(resultSet.getInt("price_per_unit"));
				product.setProductInfo(resultSet.getString("product_info"));
				
				productList.add(product);
			}
			preparedStatement.close();
            connection.close();
		}
		catch (Exception e)
		{
			throw e;
		}
		
		return	productList;
	}*/
    public void addInventory(Inventory inventory) throws Exception
    {
    	try
		{
			connection = getDataBaseConnection();
			
			query = "INSERT INTO `inventory` (`name` ,`email` ,`phone` ,`items` ,`address` ,`amount`)" +
					" VALUES (?, ?, ?, ?, ? ,?)";
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1,inventory.getName());
			preparedStatement.setString(2,inventory.getEmail());
			preparedStatement.setString(3,inventory.getPhone());
			preparedStatement.setString(4,inventory.getItems());
			preparedStatement.setString(5,inventory.getAddress());
			preparedStatement.setInt(6,inventory.getAmount());			
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
		}
		catch (Exception e)
		{			
			throw e;
		}
    }
    
    public ArrayList<Inventory> getInventoryList() throws Exception
	{	
		try
		{				
			connection = getDataBaseConnection();
			
			query = "SELECT * from inventory";
			preparedStatement = connection.prepareStatement(query);
			
			resultSet=preparedStatement.executeQuery();
			
			inventoryList = new ArrayList<Inventory>();
			
			while(resultSet.next())	
			{
				inventory = new Inventory();
				
				inventory.setInventoryID(resultSet.getInt("inventoryid"));
				inventory.setName(resultSet.getString("name"));		
				inventory.setEmail(resultSet.getString("email"));
				inventory.setPhone(resultSet.getString("phone"));
				inventory.setItems(resultSet.getString("items"));
				inventory.setAddress(resultSet.getString("address"));
				inventory.setAmount(resultSet.getInt("amount"));
				inventory.setDateTime(resultSet.getString("datetime"));
				inventory.setStatus(resultSet.getString("status"));
								
				inventoryList.add(inventory);
			}
			preparedStatement.close();
            connection.close();
		}
		catch (Exception e)
		{
			throw e;
		}
		
		return	inventoryList;
	}
    
    public void delieverd(int inventoryID) throws Exception
    {
    	try
		{
			connection = getDataBaseConnection();
			
			query = "UPDATE `inventory` SET `status` = 'Delivered' WHERE `inventoryid` = ?";
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1,inventoryID);		
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
		}
		catch (Exception e)
		{			
			throw e;
		}
    }
    
    public void register(Member member) throws Exception
    {
    	try
		{
			connection = getDataBaseConnection();
			
			query = "INSERT INTO `onlinebookstore`.`member` (`emailID` ,`phoneNo` ,`address` ,`password` ,`name`)" +
					"VALUES (?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1,member.getName());
			preparedStatement.setString(2,member.getEmail());
			preparedStatement.setString(3,member.getPhone());
			preparedStatement.setString(4,member.getPassword());
			preparedStatement.setString(5,member.getAddress());
				
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
		}
		catch (Exception e)
		{			
			throw e;
		}
    }
    
    public Member login(String email, String password) throws Exception
	{	
		try
		{				
			connection = getDataBaseConnection();
			
			query = "SELECT * from member where emailID = ? and password = ? limit 1";
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,email);
			preparedStatement.setString(2,password);
			
			resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next())	
			{
				member = new Member();
								
				member.setName(resultSet.getString("name"));		
				member.setEmail(resultSet.getString("emailID"));
				member.setPhone(resultSet.getString("phoneNo"));				
				member.setAddress(resultSet.getString("address"));
				
			}
			preparedStatement.close();
            connection.close();
		}
		catch (Exception e)
		{			
			throw e;
		}
		
		return	member;
	}
    
    
}
