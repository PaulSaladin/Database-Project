package eHotel.connections;

import java.sql.*;
import java.util.ArrayList;

import eHotel.entities.Room; 


public class  PostgreSqlConn{
	
		Connection db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Statement st = null;
	    String sql;


		public void getConn(){
			
			try {
				
				Class.forName("org.postgresql.Driver"); 
								
				db = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/psala069",
						"psala069", "Ardoise265");
															
			}catch(Exception e) {
				System.out.print("error catched");
			}
						
		}
		
		public void closeDB() {
				try {
					if(rs != null){
						rs.close();
					}
					if(ps!=null){
						ps.close();
					}
					if(st!=null){
						st.close();
					}
					if(db!=null){
						db.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		
		public String getpwdbyUname(String param){
			getConn();

			String pwd = "";
			
	        try{
	            ps = db.prepareStatement("select passworde from project.employee where namee=?");
	            
	            ps.setString(1, param);	               
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					pwd = rs.getString(1);
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return pwd;       
	    }
		
		
		public String[] getuserinforbycustSSN(String param){
			getConn();

			String[] pwd = new String[2];
			
	        try{
	            ps = db.prepareStatement("select * from ehotel.customer where customer_ssn=?");
	            
	            ps.setString(1, param);	               
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					pwd[0] = rs.getString(2);
					pwd[1] = rs.getString(3);
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return pwd;       
	    }
		
		public boolean insertNewCustomer(String[] param){
			getConn();

			
	        try{
	        	st = db.createStatement();
	        	sql = "insert into ehotel.customer values('"+param[0]+"','"+param[1]+"','"+param[2]+"')";
	        	
	        	System.out.print(sql);
	            
	            st.executeUpdate(sql);
	            
	            return true;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return false;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		public  ArrayList<Room> getAllAvailRooms(){
			
			getConn();
			
			ArrayList<Room> Rooms = new ArrayList<Room>();
			
			try {
				ps = db.prepareStatement("select * from ehotel.room where room_status='available'" );
				rs = ps.executeQuery();
				while(rs.next()){
					Integer room_no = Integer.parseInt(rs.getString("room_no"));
					String room_status = rs.getString("room_status");
					Room room = new Room(room_no, room_status);
					Rooms.add(room);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return Rooms;
			
		}
		
		//aaa
		
		public  ArrayList<Room> getbookedRooms(String custSSN){
			
			getConn();
			
			ArrayList<Room> Rooms = new ArrayList<Room>();
			
			try {
				ps = db.prepareStatement("select * from ehotel.room where customer_ssn='"+custSSN+"'");
				rs = ps.executeQuery();
				while(rs.next()){
					Integer room_no = Integer.parseInt(rs.getString("room_no"));
					String room_status = rs.getString("room_status");
					Room room = new Room(room_no, room_status);
					Rooms.add(room);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return Rooms;
			
		}
		
		public String bookRoom(String custName, String roomno){
			getConn();
			String custSSN="";
			
	        try{
	        	
	        	ps = db.prepareStatement("select customer_ssn from ehotel.customer where customer_name='"+custName+"'");
				rs = ps.executeQuery();
				
				while(rs.next()){
					custSSN = rs.getString("customer_ssn");
				}
				
				
	        	st = db.createStatement();
	        	sql = "update ehotel.room set customer_ssn='"+custSSN+"', room_status='booked' where room_no='"+roomno+"'";
	            st.executeUpdate(sql);
	            
	            
	            return custSSN;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return "";	 
	        }finally {
	        	closeDB();
	        }
			      
	    }
		
		public String rentRoom(String ssncustomer, String idroom, String idBooking, String dateStart, String dateEnd, String nameHotel){
			getConn();
			String custSSN="";
			
	        try{
	        	if(idBooking == null) {
	        		String values = "('" + idroom + "','" +  ssncustomer + "','" + nameHotel + "','" + dateStart + "','" + dateEnd + "')";
	        		ps = db.prepareStatement("INSERT INTO Renting(idroom, idbooking, ssncustomer, namehotel, dateStart, dateEnd) VALUES"+values);
	        		ps.executeQuery();  
	        	}
	        	else {
	        		ps = db.prepareStatement("select idroom, ssncustomer, namehotel, dateStart, dateEnd from ehotel.booking where idbooking='"+idBooking+"'");
	        		rs = ps.executeQuery();
	        		String valuesb = "('" + rs.getString(1) + "','" + idBooking + "','" + rs.getString(2) + "','" + rs.getString(3) + "','" + rs.getString(4) + "','" + rs.getString(5) + "')";
	        		ps = db.prepareStatement("INSERT INTO Renting(idroom, idbooking, ssncustomer, namehotel, dateStart, dateEnd) VALUES"+valuesb);
	        		ps.executeQuery();
	        	}

	            return custSSN;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return "";	 
	        }finally {
	        	closeDB();
	        }
			      
	    }


		
	}

