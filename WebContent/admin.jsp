<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="com.Utils"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.Inventory"%>

<%

ArrayList<Inventory> inventoryList;
Utils utils = new Utils();
inventoryList =  utils.getInventoryList();

String admin = (String)session.getAttribute("Admin");
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<%@ include file="include.html" %>

<body>
<div >
</div>
<div id="wrapper">

	<div id="header-container">     	    	
	ONLINE BOOK STORE      
	</div>
	
	<div id="menu">
		<table>
			<tr>
				<td><a href="./">Home</a></td>
				<%
				if(admin != null)
				{
				%>
				<td><a href="./Controller?operation=logout">Log out</a></td>
				<%
			    }
			    %> 
			</tr>
		</table>
	</div>
        
  	<div id="content-container"> 
  		<div id="product_list">   	
        <%
        
        if(admin == null)
        {
        %>
        	<h3>Enter Password to see Inventories</h3> 
        	<hr/> 
        	<div>
        		<br/><br/><br/><br/><br/><br/><br/><br/><br/>
        		<form action="./Controller" method="post" style="text-align:center">
        			<input type="password" class="forminput" maxlength="50" size="50" name="password" required placeholder="Enter Password..."/>
        			<br/><br/>
        			<input type="submit" class="formbutton" value="Submit"/> 
        			<input type="hidden" name="operation" value="validateAdmin" />
        		</form>
        	</div>
        <%
        }
        else
        {
        %>
       	
       		<h3>Inventories</h3> 
       		<hr/> 
       		<table width="100%">
       			<thead>
       				<tr>
       					<td>Order ID</td>
       					<td>Name</td>
       					<td>Email</td>
       					<td>Phone No</td>
       					<td>Items</td>
       					<td>Address</td>
       					<td>Amount</td>
       					<td>Date & Time</td>
       					<td>Status</td>
       					
       				</tr>
       			</thead>
       			<tbody>
       			<%
       			for(Inventory inventory : inventoryList) 
       			{
       			%>
       				<tr>
       					<td><%=inventory.getInventoryID() %></td>
       					<td><%=inventory.getName() %></td>
       					<td><%=inventory.getEmail() %></td>
       					<td><%=inventory.getPhone() %></td>
       					<td><%=inventory.getItems() %></td>
       					<td><%=inventory.getAddress() %></td>
       					<td><%=inventory.getAmount() %></td>
       					<td><%=inventory.getDateTime() %></td>
       					<td>
       					<%
       					if(inventory.getStatus().equals("Pending"))
       					{
       						%>
       						<a href="./Controller?operation=delivered&inventoryid=<%=inventory.getInventoryID() %>" title="Change status to delieverd"><%=inventory.getStatus() %></a>
       						<%
       					}
       					else
       					{
       						%>
       						<%=inventory.getStatus() %>
       						<%
       					}
       					%>
       					</td>       					
       				</tr>
       			<%
       			}
       			%>
       			</tbody>
       		</table>
       		
    	
    	<%
    	}
    	%>
    	</div>      	
    </div>    
    
    <%@ include file="footer.html" %>
    
</div>

</body>
</html>
