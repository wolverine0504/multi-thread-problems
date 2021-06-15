package sleeping_barber;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BarberShop {

	Semaphore barberChair;
	Semaphore waitingRoom;
	AtomicInteger waitingSeatsAvailable;
	AtomicInteger waitingChairs;
	AtomicBoolean Alldone;
	int MaxCustomers;
	int MaxBarbers;

	public BarberShop() {
		// TODO Auto-generated constructor stub
	}

	public BarberShop(Semaphore barberChair,Semaphore waitingRoom, AtomicInteger waitingSeatsAvailable, AtomicInteger waitingChairs,
			AtomicBoolean alldone, int maxCustomers, int maxBarbers) {
		super();
		this.barberChair = barberChair;
		this.waitingRoom=waitingRoom;
		this.waitingSeatsAvailable = waitingSeatsAvailable;
		this.waitingChairs = waitingChairs;
		this.Alldone = alldone;
		this.MaxCustomers = maxCustomers;
		this.MaxBarbers = maxBarbers;
	}

	public String getCurrentTime() {
		Date nowTime = new Date(System.currentTimeMillis());
		SimpleDateFormat sdFormatter = new SimpleDateFormat("HH:mm:ss:SSS");
		String retStrFormatNowDate = sdFormatter.format(nowTime);
		return retStrFormatNowDate;
	}

}
