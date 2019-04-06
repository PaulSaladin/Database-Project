package eHotel.connections;

import java.sql.*;
import java.util.ArrayList;

import eHotel.entities.Room; 
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
	            System.out.println(e);
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
		
		public  ArrayList<Room> getAllAvailRooms(){
			
			getConn();
			
			ArrayList<Room> Rooms = new ArrayList<Room>();
			
			try {
				ps = db.prepareStatement("select * from project.room" );
				rs = ps.executeQuery();
				while(rs.next()){
					Integer idroom = Integer.parseInt(rs.getString("idroom"));
					String nameHotel = rs.getString("namehotel");
					//Room room = new Booking(room_no, room_status);
					//Rooms.add(room);
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
		
		public ArrayList<String> getAllHotelChains(){
			ArrayList<String> names = new ArrayList<String>();
			try {
				ps = db.prepareStatement("select namehotelchain from project.hotelchain");
				rs = ps.executeQuery();
				while(rs.next()){
					String nameHotelChain = rs.getString("namehotelchain");
					names.add(nameHotelChain);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return names;
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
		
		public String rentRoomOnlyWithIdBooking(String idBooking){
			String idRenting = "";
            getConn();
            int count = 0;
            String values ="";

            try {
                ps = db.prepareStatement("select * from project.booking where idbooking="+idBooking + " and rented = false ");
                rs = ps.executeQuery();
                while(rs.next()){
                    count += 1;
                    String idroom = rs.getString("idroom");
                    String nameHotel = rs.getString("namehotel");
                    String dateStart = rs.getString("datestart");
                    String dateEnd = rs.getString("dateend");
                    String custSSN = rs.getString("ssncustomer");
                    values = "("+idBooking+", "+idroom+", '"+nameHotel+"', "+custSSN+", '"+dateStart+"', '"+dateEnd+"')";
                }

                if(count == 0) {
                    return "";
                }
                ps = db.prepareStatement("insert into project.renting (idbooking, idroom, nameHotel, ssncustomer, datestart, dateend) values " + values);
                ps.executeUpdate();
                ps = db.prepareStatement("UPDATE project.booking SET rented=true WHERE idbooking =" + idBooking);
                ps.executeUpdate();
                ps = db.prepareStatement("select idrenting from project.renting where(idbooking =" + idBooking +")");
                rs = ps.executeQuery();
                while(rs.next()) {
                	idRenting = rs.getString("idrenting");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeDB();
            }

            return idRenting;
        }
		

		public ArrayList<String[]> getRoomsOKInHotel(String hotel, String priceMin, String priceMax, String capacity){
			System.out.println("entrée getRoomsOK");
			ArrayList<String[]> rooms = new ArrayList<String[]>();
			try {
				
				String roomCondition = "";
					
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
					String idroom = rs.getString("idroom");
					String price = rs.getString("price");
					String hotelName = rs.getString("namehotel");
					String[] room = {idroom, hotelName, price};
					rooms.add(room);
				}
				rs.close();
				
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
						
			return rooms;
			
		}
		
		public  ArrayList<String[]> getAllAvailRoomsInHotel(String hotel, String dateStart, String dateEnd, String capacity, String priceMin, String priceMax){
			getConn();
			
			ArrayList<String[]> Rooms = new ArrayList<String[]>();
			
			try {
				ArrayList<String[]> rooms = getRoomsOKInHotel(hotel,priceMin,priceMax,capacity);
				String bookingCondition = "";
				System.out.println("entré getAllAvailRooms avant boucle OK");
				System.out.println(rooms.size());
				for(String[] room : rooms) {
					int count = 0;
					bookingCondition = " where ( idroom = " + room[0] + " and namehotel ='" + hotel + "' and datestart <= '" + dateEnd + "' and dateend > '" + dateStart + "')";
					ps = db.prepareStatement("select idroom from project.booking"+bookingCondition);
					rs = ps.executeQuery();
					while(rs.next()) {
						count += 1;
					}
					System.out.println(count);
					rs.close();
					ps = db.prepareStatement("select idroom from project.renting"+bookingCondition);
					rs = ps.executeQuery();
					while(rs.next()) {
						count += 1;
					}
					System.out.print(count);
					if(count==0) {
						System.out.println(room[0]);
						System.out.println(room[1]);
						System.out.println(room[2]);
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
		
		public String getHotelnameByEmployeeId(String employee_id) {
			getConn();
			String hotelName = "";
	        try{
	        	
        		ps = db.prepareStatement("select namehotel from project.employee where namee = '"+ employee_id +"'");
        		System.out.println("select namehotel from project.employee where namee = '"+ employee_id +"'");
        		ResultSet rs = ps.executeQuery();  
        		while(rs.next()) {
        			hotelName = rs.getString("namehotel");
        		}
        		rs.close();
        		ps.close();

	        }catch(SQLException e){
	            System.out.println(e);
	        }finally {
	        	closeDB();
	        }
			      
			return hotelName;
		}
		
		public void pay(String idrentingEntered, String amount) {
			getConn();
			
	        try{
	        	
        		ps = db.prepareStatement("insert into project.payment (amount,idrenting) values ("+ amount + "," + idrentingEntered +")" );
        		ps.executeUpdate();  

	        }catch(SQLException e){
	            System.out.println(e);
	        }finally {
	        	closeDB();
	        }
			
		}
		
		public String rentRoom2(String room, String hotelname, String dateStart, String dateEnd, String ssncustomer){
			String idRenting = "";
            getConn();
            String values ="("+ room + ",'" + hotelname + "'," + ssncustomer + ",'" + dateStart + "','" + dateEnd+ "')";
            System.out.println("insert into project.renting (idroom, nameHotel, ssncustomer, datestart, dateend) values "+ values);

            try {
                ps = db.prepareStatement("insert into project.renting (idroom, nameHotel, ssncustomer, datestart, dateend) values "+ values );
                ps.executeUpdate();
                
                ps = db.prepareStatement("select idrenting from project.renting where (idroom = "+ room +" and nameHotel = '" + hotelname
                		+ "' and ssncustomer = " + ssncustomer + " and datestart = '" + dateStart + "' and dateend = '"+dateEnd+"')");
                rs = ps.executeQuery();
                while(rs.next()) {
                	idRenting = rs.getString("idrenting");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeDB();
            }

            return idRenting;
        }
		
		
	public String getPriceByBooking(String idbooking) {
		getConn();
		String hotelName = "";
		String idroom = "";
		String price = "";
        try{
        	
    		ps = db.prepareStatement("select idroom, namehotel from project.booking where idbooking = "+ idbooking );
    		ResultSet rs = ps.executeQuery();  
    		while(rs.next()) {
    			hotelName = rs.getString("namehotel");
    			idroom = rs.getString("idroom");
    		}
    		rs.close();
    		
    		ps = db.prepareStatement("select price from project.room where (idroom = "+ idroom +" and namehotel ='" +hotelName + "')" );
    		rs = ps.executeQuery();  
    		while(rs.next()) {
    			price = rs.getString("price");
    		}
    		rs.close();
    		ps.close();
    			
        }catch(SQLException e){
            System.out.println(e);
        }finally {
        	closeDB();
        }
		      
		return price;
	}
		
		
}

