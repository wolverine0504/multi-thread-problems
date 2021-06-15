package dining_philosophers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
	public static void main(String[] args) {
		Philosopher[] philosophers= new Philosopher[5];
		Fork[] forks=new Fork[philosophers.length];
		
		for (int i = 0; i < forks.length; i++) {
			forks[i]=new Fork(i+1);
		}
		
		for (int i = 0; i < philosophers.length; i++) {
			Fork leftfork=forks[i];
			Fork rightfork=forks[(i+1)%forks.length];
			
			if(i==philosophers.length-1) {//最後一個ph
				System.out.println("last ph");
				philosophers[i]=new Philosopher(rightfork, leftfork);
			}else {
				philosophers[i]=new Philosopher(leftfork, rightfork);
			}
			//philosophers[i]=new Philosopher(leftfork, rightfork);
			new Thread(philosophers[i],"Philosopher"+(i+1)).start();
			
		}
		
	}

}
