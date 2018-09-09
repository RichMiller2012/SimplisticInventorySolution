package util;

public enum Months {

	JANUARY("January", 1),
	FEBRUARY("February", 2),
	MARCH("March", 3),
	APRIL("April", 4),
	MAY("May", 5),
	JUNE("June", 6),
	JULY("July", 7),
	AUGUST("August", 8),
	SEPTEMBER("September", 9),
	OCTOBER("October", 10),
	NOVEMBER("November", 11),
	DECEMBER("December", 12);
	
	private final String month;
	private final Integer value;
	
	Months(String month, Integer value){
		this.month = month;
		this.value = value;
	}
	
	public String getMonth() {
		return this.month;
	}
	
	public Integer returnValue() {
		return this.value;
	}
	
	public static Integer getValueOfMonth(String month) {
		for(Months m : Months.values()) {
			if(m.getMonth().equals(month)) {
				return m.value;
			}
		}
		return 0;
	}
	
}
