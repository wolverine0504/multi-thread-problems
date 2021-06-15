package sleeping_barber;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

	public static void main(String[] args) {

		AtomicInteger waitingChairs = new AtomicInteger(3);
		AtomicInteger waitingSeatsAvailable = waitingChairs;
		int maxCustomers = 5;
		int maxBarbers = 2;

		Semaphore barberChair = new Semaphore(maxBarbers);
		Semaphore waitingRoom = new Semaphore(waitingChairs.get());
		AtomicBoolean alldone = new AtomicBoolean(false);
	
		BarberShop barbershop = new BarberShop(barberChair, waitingRoom, waitingSeatsAvailable, waitingChairs, alldone,
				maxCustomers, maxBarbers);
		//System.out.println(barbershop.waitingRoom);
		
		Barber[] barbers = new Barber[maxBarbers];
	

		for (int i = 0; i < barbers.length; i++) {
			Semaphore pillow = new Semaphore(0);
			Semaphore seatBelt = new Semaphore(0);

			AtomicBoolean barberSleep = new AtomicBoolean(true);
			AtomicBoolean barberOnWork = new AtomicBoolean(false);

			Barber barber = new Barber(pillow, seatBelt, barberSleep, barberOnWork, barbershop, i + 1);
			barbers[i]=barber;
			new Thread(barber).start();

		}
		
		

		Thread[] Customerthreads = new Thread[maxCustomers];
		for (int i = 0; i < maxCustomers; i++) {
			Customerthreads[i] = new Thread(new Customer(barbershop, i + 1, barbers));
			Customerthreads[i].start();
		}
		
		///main thread wait for all customers thread be done
		for (int i = 0; i < maxCustomers; i++) {
			

			try {
				Customerthreads[i].join();
			} catch (InterruptedException e) {
				 //TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		barbershop.Alldone.getAndSet(true);
		System.out.println("Customers are all served!!! Nice Job " + barbershop.getCurrentTime());
		return ;
	}

}
