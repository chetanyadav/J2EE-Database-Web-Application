package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class Controller
 */

public class Admin extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private PrintWriter printWriter; 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		ArrayList<Inventory> inventoryList = null;
		Utils utils = new Utils();
		try {
			inventoryList =  utils.getInventoryList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		printWriter=response.getWriter();
		response.setContentType("text/html");
		
		printWriter.println("<h3>Inventories</h3> <hr/> ");
		printWriter.println("<table width=\"100%\">");
		printWriter.println("<thead><tr>");
		printWriter.println("<td>Order ID</td><td>Name</td><td>Email</td><td>Phone No</td><td>Items</td><td>Address</td><td>Amount</td><td>Date & Time</td><td>Status</td>");
		printWriter.println("</thead></tr>");
		
		printWriter.println("<tbody><tr>");
		
		for(Inventory inventory : inventoryList) 
		{
			printWriter.println("<tr>");
			printWriter.println("<td>"+inventory.getInventoryID()+"</td>");
			printWriter.println("<td>"+inventory.getName()+"</td>");
			printWriter.println("<td>"+inventory.getEmail()+"</td>");
			printWriter.println("<td>"+inventory.getPhone()+"</td>");
			printWriter.println("<td>"+inventory.getItems()+"</td>");
			printWriter.println("<td>"+inventory.getAddress()+"</td>");
			printWriter.println("<td>"+inventory.getAmount()+"</td>");
			printWriter.println("<td>"+inventory.getDateTime()+"</td>");
			
			printWriter.println("<td>");
			if(inventory.getStatus().equals("Pending"))
			{
				printWriter.println("<a href=\"./Controller?operation=delivered&inventoryid="+inventory.getInventoryID()+"\" title=\"Change status to delieverd\">"+inventory.getStatus()+"</a>");					
			}
			else
			{
				printWriter.println(inventory.getStatus());
			}
			printWriter.println("</td>");			
			printWriter.println("</tr>");
		}
		
		printWriter.println("</tbody></tr>");
		printWriter.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request,response);
	}

}
