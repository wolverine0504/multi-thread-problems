package dining_philosophers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Philosopher implements Runnable {

	private final Fork leftfork;
	private final Fork rightfork;

	public Philosopher(Fork leftfork, Fork rightfork) {
		this.leftfork = leftfork;
		this.rightfork = rightfork;
	}

	public void think() throws InterruptedException {
		System.out.println(Thread.currentThread().getName()+" start thinking at "+getCurrentTime());
		Thread.sleep((int)(Math.random()*3000));
	}
	
	public void pickUpFork(Fork fork,String lr) throws InterruptedException {
		if(lr=="left") {
			System.out.println(Thread.currentThread().getName()+" pick up his left fork:fork"+fork.getNumber()+" at "+getCurrentTime());
			Thread.sleep((int)(Math.random()*100));
		}else {
			System.out.println(Thread.currentThread().getName()+" pick up his right fork:fork"+fork.getNumber()+" at "+getCurrentTime());
		}
		
	}
	
	public void putDownFork(Fork fork,String lr)throws InterruptedException {
		if(lr=="left") {
			System.out.println(Thread.currentThread().getName()+" put down his left fork:fork"+fork.getNumber()+" at "+getCurrentTime());
			
		}else {
			System.out.println(Thread.currentThread().getName()+" put down his right fork:fork"+fork.getNumber()+" at "+getCurrentTime());
			Thread.sleep((int)(Math.random()*100));
		}
		
	}
	
	public void eat()throws InterruptedException {
		System.out.println(Thread.currentThread().getName()+" start eating at "+getCurrentTime());
		Thread.sleep((int)(Math.random()*3000));
	}

	@Override
	public void run() {
		try {
			while(true) {
				think();
				synchronized (leftfork) {
					pickUpFork(leftfork,"left");
					synchronized (rightfork) {
						pickUpFork(rightfork, "right");
						eat();
						putDownFork(rightfork, "right");
					}
					putDownFork(leftfork, "left");					
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}
	
	
	public String getCurrentTime() {
		Date nowTime = new Date(System.currentTimeMillis());
		SimpleDateFormat sdFormatter = new SimpleDateFormat("HH:mm:ss:SSS");
		String retStrFormatNowDate = sdFormatter.format(nowTime);
		return retStrFormatNowDate;
	}

}
