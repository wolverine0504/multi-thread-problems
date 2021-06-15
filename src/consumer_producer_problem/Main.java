package consumer_producer_problem;

import java.util.ArrayDeque;
import java.util.Queue;

public class Main {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		Queue<Product> queue=new ArrayDeque<>();
		
		for (int i = 0; i < 5; i++) {
			
			new Thread(new Producer(queue,2)).start();
			
		}
		for (int i = 0; i < 5; i++) {
			new Thread(new Consumer(queue,2)).start();
		}
	}

}
