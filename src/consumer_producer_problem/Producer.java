package consumer_producer_problem;

import java.util.Queue;

public class Producer implements Runnable {

	private Queue<Product> queue;
	private int max;

	public Producer(Queue queue, int max) {
		this.queue = queue;
		this.max = max;
	}

	@Override
	public void run() {
		synchronized (queue) {
			while (queue.size() >= max) {// call wait to wait
				try {
					System.out.println("Producer" + Thread.currentThread().getName()
							+ " start waiting...Queue reach max capacity");
					queue.wait();
					/// thread執行到這就代表等待結束
					System.out.println("Producer" + Thread.currentThread().getName() + " exit waiting state");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (queue.size() == 0) {
				System.out.println("Producer" + Thread.currentThread().getName() + " wake up all waiting Consumers");
				queue.notifyAll();
			}
			int Max = 100;
			int Min = 0;
			int ran_int = Min + (int) (Math.random() * ((Max - Min) + 1));//
			queue.offer(new Product("Product" + String.valueOf(ran_int)));
			System.out.println(
					"Producer" + Thread.currentThread().getName() + " produce Product" + String.valueOf(ran_int));
			System.out.print("current Products:");
			for (Product p : queue) {
				System.out.print(p.getName() + " ");
			}
			System.out.println();

		}
	}

}
