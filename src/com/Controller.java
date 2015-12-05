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

public class Controller extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private PrintWriter printWriter; 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String contextPath = request.getContextPath();
		printWriter=response.getWriter();
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		Inventory inventory = new Inventory();
		Utils utils = new Utils();
		Member member = new Member();
		
		if(request.getParameterMap().containsKey("operation"))
		{	
			if(request.getParameter("operation").equals("buy"))
			{				
				String[] books = request.getParameterValues("book");
				
				int bookNo = 1, price = 0, noOfBooksSelected = 0, totalPrice = 0;
				String bookName, items = "", itemName;
				
				if(books == null)
				{
					printWriter.println("<script>alert(\"Choose atleast one book to procees.\")</script>;");
					printWriter.println("<script>window.location=\"./\";</script>");							
				}
				else
				{
					noOfBooksSelected = books.length;
					
					while(bookNo<=noOfBooksSelected)
					{							
						itemName = books[bookNo-1];						
						
						price = Integer.parseInt(itemName.split("-")[1]);
						bookName = itemName.split("-")[0];
						
						items = items+bookName+", ";
						totalPrice+=price;
						
						bookNo++;
					}
					
					inventory.setAmount(totalPrice);
					inventory.setItems(items);
					
					session.setAttribute("Inventory", inventory);
					
					member = (Member)session.getAttribute("MemberInContext");
					
					if(member == null)
					{						
						printWriter.println("<script>window.location=\"./login.html\";</script>");
					}
					else
					{
						inventory = (Inventory)session.getAttribute("Inventory");
						addInventory(member,inventory);
					}
				}
			}
			
			else if(request.getParameter("operation").equals("login"))
			{
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				
				member = new Member();
				try 
				{
					member = utils.login(email,password);
					if(member == null)
					{
						printWriter.println("<script>alert(\"Invalid credentials!!!Try again\")</script>");						
						printWriter.println("<script>window.location=\"./login.jsp\";</script>");						
					}
					else 
					{
						session.setAttribute("MemberInContext", member);												
					}
					
					inventory = (Inventory)session.getAttribute("Inventory");
					if(inventory!=null)
					{	
						addInventory(member,inventory);
					}
					else
					{
						printWriter.println("<script>alert(\"Succesfull Logged in\")</script>");					
						printWriter.println("<script>window.location=\"./\";</script>");	
					}
				} 
				catch (Exception e) 
				{
					System.out.println(e);
					printWriter.println("<script>alert(\"Failure Occured\")</script>");					
					printWriter.println("<script>window.location=\"./\";</script>");					
				}
				
			}

			else if(request.getParameter("operation").equals("logout"))
			{				
				session.setAttribute("MemberInContext", null);
				session.setAttribute("Inventory", null);
				session.invalidate();
				printWriter.println("<script>window.location=\"./\";</script>");
			}
			
			else if(request.getParameter("operation").equals("register"))
			{
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				String phone = request.getParameter("phone");
				String address = request.getParameter("address");
											
				member.setAddress(address);
				member.setEmail(email);
				member.setName(name);
				member.setPassword(password);
				member.setPhone(phone);
				
				try 
				{
					utils.register(member);
					session.setAttribute("MemberInContext", member);
					
					inventory = (Inventory)session.getAttribute("Inventory");
					if(inventory!=null)
					{	
						addInventory(member,inventory);
					}
					else
					{
						printWriter.println("<script>alert(\"Registered Successfull\")</script>");
						printWriter.println("<script>window.location=\"./\";</script>");
					}
				} 
				catch (Exception e) 
				{					
					printWriter.println("<script>alert(\"Failure Occurred\")</script>");
					printWriter.println("<script>window.location=\"./\";</script>");
				}
			}
			else if(request.getParameter("operation").equals("delivered"))
			{				
				int inventoryID = Integer.parseInt(request.getParameter("inventoryid"));
				try 
				{
					utils.delieverd(inventoryID);					
				} 
				catch (Exception e) 
				{
					printWriter.println("<script>alert(\"Update Failed!!!\nTry again\")</script>");							
				}
				printWriter.println("<script>window.location=\"./Admin\";</script>");
			}
			else
			{
				response.sendRedirect(contextPath);
			}
		}
		else
		{
			response.sendRedirect(contextPath);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request,response);
	}

	protected void addInventory(Member member, Inventory inventory)
	{
		Utils utils = new Utils();
		
		inventory.setAddress(member.getAddress());
		inventory.setEmail(member.getEmail());
		inventory.setName(member.getName());
		inventory.setPhone(member.getPhone());
		
		try 
		{					
			utils.addInventory(inventory);			
			printWriter.println("<script>alert(\"Your Order is Placed\")</script>");	
		}
		catch (Exception e) 
		{
			printWriter.println("<script>alert(\"Your Order failed to submit\")</script>");	
		}
		finally
		{
			printWriter.println("<script>window.location=\"./\";</script>");
		}
		
	}
}
