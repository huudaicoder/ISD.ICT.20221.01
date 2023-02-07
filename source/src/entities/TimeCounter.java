package entities;

import java.util.Calendar;
import java.util.Date;

/**
 * A time counter is in charge of recording the start time, end time, as well as returning the time a bike is rented
 * @author daidh
 *
 */
public class TimeCounter {
	private boolean activate;
	private Date start, end;
	public TimeCounter() {
		this.activate = false;
	}
	
	public Date startCounter() {
		if (!this.activate) {
			this.activate = true;
			start = Calendar.getInstance().getTime();
		}
		return start;
	}
	
	public Date stopCounter() {
		end = Calendar.getInstance().getTime();
		return end;
	}
	
	public void setActive(boolean active) {
		this.activate = active;
	}

}
