package consumer_producer_problem;

import java.util.Queue;

public class Consumer implements Runnable {
	
	private Queue<Product> queue;
	private int max;

	public Consumer(Queue queue,int max) {
		this.max=max;
		this.queue=queue;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized(queue) {
			while(queue.isEmpty()) {//等待到拿鎖到鎖之間可能有其他thread先拿到鎖 queue可能又空了 所以要再判斷一次
				try {
					System.out.println("Consumer" + Thread.currentThread().getName()
							+ " start waiting...Queue is empty");
					queue.wait(); //not just wait()
					/// thread執行到這就代表等待結束
					System.out.println("Consumer" + Thread.currentThread().getName() + " exit waiting state");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(queue.size()==max) {
				System.out.println("Consumer" + Thread.currentThread().getName()+" wake up all waiting Producers");
				queue.notifyAll();///不會叫醒到consumers
			}
			Product product=queue.poll();
			System.out.println(
					"Consumer" + Thread.currentThread().getName() + " consume " +product.getName());
			System.out.print("current Products: ");
			for(Product p : queue) { 
				  System.out.print(p.getName()+" "); 
				}
			System.out.println();
		}
		
	}

}
