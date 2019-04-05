package eHotel.entities;

public class Booking {
	
	private Integer idRoom;
	private String nameHotel;
	private Integer ssncustomer;
	private String datestart;
	private String dateend;
	
	public Booking() {
		
	}
	
	public Booking(Integer idRoom, String nameHotel, Integer ssncustomer, String datestart, String dateend) {
		this.idRoom = idRoom;
		this.nameHotel = nameHotel;
		this.ssncustomer = ssncustomer;
		this.datestart = datestart;
		this.dateend = dateend;
	}
	
	public String getIdRoom() {
		String roomId = ""+idRoom;
		return roomId;
	}
	
	public String getNameHotel() {
		return nameHotel;
	}
	
	public Integer getSsnCustomer() {
		return ssncustomer;
	}
	
	public String getDateStart() {
		return datestart;
	}
	
	public String getDateEnd() {
		return dateend;
	}
	
}

