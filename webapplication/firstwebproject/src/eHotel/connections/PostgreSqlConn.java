package eHotel.connections;

import java.sql.*;
import java.util.ArrayList;

import eHotel.entities.Booking;


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
		
		
		public String[] getuserinforbycustSSN(Integer param){
			getConn();

			String[] pwd = new String[2];
			
	        try{
	            ps = db.prepareStatement("select password,name from project.customer where ssn=" + param);
	            
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					pwd[0] = rs.getString(1);
					pwd[1] = rs.getString(2);
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
	        	sql = "insert into project.customer values("+param[0]+",'"+param[1]+"','"+param[2]+"',now(),'"+param[3]+"')";
	        	
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
		
		public ArrayList<String> getHotelsOK(String hotelChain, String rate, String area, String minRoom, String maxRoom){
			System.out.println("entrée getHotelsOK");
			ArrayList<String> hotels = new ArrayList<String>();
			try {
				int count = 5;
				String hotelCondition = " where (";
				if(hotelChain.equals("")) {
					count -= 1;
				}
				else {
					hotelCondition += "namehotelchain = '" + hotelChain + "' and ";
				}
				if(rate.equals("")) {
					count -=1;
				}
				else {
					hotelCondition += "rating = " + rate +" and ";
				}
				if (minRoom.equals("")) {
					count -=1;
				}
				else {
					hotelCondition += "numroom >= " + minRoom+ " and ";
				}
				if (maxRoom.equals("")) {
					count -=1;
				}
				else {
					hotelCondition += "numroom <= " + maxRoom+ " and ";
				}
				if(area.equals("")) {
					hotelCondition = hotelCondition.substring(0, hotelCondition.length()-4);
					count -=1;
				}
				else {
					hotelCondition += "area ='" + area +"'";
				}
				hotelCondition += ")";
				if (count == 0) {
					hotelCondition = "";
				}
				ps = db.prepareStatement("select namehotel from project.hotel"+hotelCondition);
				rs = ps.executeQuery();
				while(rs.next()){
					String nameHotel = rs.getString("namehotel");
					hotels.add(nameHotel);
				}
				rs.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}						
			return hotels;
			
		}
		
		public ArrayList<String[]> getRoomsOK(ArrayList<String> hotels, String priceMin, String priceMax, String capacity){
			System.out.println("entrée getRoomsOK");
			ArrayList<String[]> rooms = new ArrayList<String[]>();
			try {
				
				String roomCondition = "";
				
				for(String hotel : hotels) {
					
					roomCondition = " where ( namehotel = '" + hotel + "' and ";

					if (priceMin.equals("")) {
					}
					else {
						roomCondition += "price >= " + priceMin+ " and ";
					}
					if (priceMax.equals("")) {
					}
					else {
						roomCondition += "price <= " + priceMax+ " and ";
					}
					if(capacity.equals("")) {
						roomCondition = roomCondition.substring(0, roomCondition.length()-4);
					}
					else {
						roomCondition += "capacity =" + capacity;
					}
					roomCondition += ")";
					
					ps = db.prepareStatement("select idroom,namehotel,price from project.room"+roomCondition);
					rs = ps.executeQuery();
					while(rs.next()){
						//System.out.println(test);
						String nameHotel = rs.getString("namehotel");
						String idroom = rs.getString("idroom");
						String price = rs.getString("price");
						String[] room = {idroom, nameHotel, price};
						rooms.add(room);
					}
					rs.close();
				}
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
						
			return rooms;
			
		}
		
		public  ArrayList<String[]> getAllAvailRooms(String dateStart, String dateEnd, String capacity, String priceMin, String priceMax, String hotelChain, String rate, String area, String minRoom, String maxRoom){
			System.out.println("entré getAllAvailRooms OK");
			getConn();
			
			ArrayList<String[]> Rooms = new ArrayList<String[]>();
			
			try {
				System.out.println("entré getAllAvailRooms try OK");
				ArrayList<String> hotels = getHotelsOK(hotelChain, rate, area, minRoom, maxRoom);
				ArrayList<String[]> rooms = getRoomsOK(hotels,priceMin,priceMax,capacity);
				String bookingCondition = "";
				System.out.println("entré getAllAvailRooms avant boucle OK");
				System.out.println(hotels.size());
				System.out.println(rooms.size());
				for(String[] room : rooms) {
					int count = 0;
					bookingCondition = " where ( idroom = " + room[0] + " and namehotel ='" + room[1] + "' and datestart <= '" + dateEnd + "' and dateend > '" + dateStart + "')";
					ps = db.prepareStatement("select idroom,namehotel from project.booking"+bookingCondition);
					rs = ps.executeQuery();
					while(rs.next()) {
						count += 1;
					}
					bookingCondition = " where ( idroom = " + room[0] + " and namehotel ='" + room[1] + "' and datestart <= '" + dateEnd + "' and dateend > '" + dateStart + "')";
					ps = db.prepareStatement("select idroom,namehotel from project.renting"+bookingCondition);
					rs = ps.executeQuery();
					while(rs.next()) {
						count += 1;
					}
					if(count==0) {
						Rooms.add(room);
					}
				}
			
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			} finally {
	        	closeDB();
	        }
						
			return Rooms;
		}
		
		//aaa
		
		public  ArrayList<Booking> getbookedRooms(String custSSN){
			
			getConn();
			Integer ssncustomer = Integer.parseInt(custSSN);
			
			ArrayList<Booking> Bookings = new ArrayList<Booking>();
			
			try {
				ps = db.prepareStatement("select * from project.booking where ssncustomer="+custSSN);
				rs = ps.executeQuery();
				while(rs.next()){
					Integer idroom = Integer.parseInt(rs.getString("idroom"));
					String nameHotel = rs.getString("namehotel");
					String dateStart = rs.getString("datestart");
					String dateEnd = rs.getString("dateend");
					Booking booking = new Booking(idroom, nameHotel, ssncustomer, dateStart, dateEnd);
					Bookings.add(booking);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return Bookings;
			
		}
		
		
		public void createBooking(String idRoom, String nameHotel, String userSSN, String dateStart, String dateEnd) {
			getConn();
			try {
				String values = "(" + idRoom + ",'" + nameHotel +"', " + userSSN +  ", '" + dateStart +"', '" + dateEnd + "')";
				ps = db.prepareStatement("Insert INTO project.booking(idroom, namehotel, ssncustomer, datestart, dateend) VALUES " + values);
				ps.executeUpdate();			

	        }catch(SQLException e){
	            e.printStackTrace();	 
	        }finally {
	        	closeDB();
	        }
		}
		


		
	}

