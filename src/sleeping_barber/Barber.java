package sleeping_barber;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class Barber implements Runnable {
	//都只會是0or1
	Semaphore pillow; //用semaphore來控制 barber 的 thread執行與否 枕頭==0 thread就會停下來 相當於barber去睡 顧客release枕頭之後 thread就會繼續執行 不能睡
	Semaphore seatBelt;//用semaphore來控制 customer 的 thread執行與否 每個barber的椅子 有一個seatbelt 在剪完頭後 customer 離開

	AtomicBoolean barberSleep;
	///只有barber親自確認waitingRoom沒有人 才會把sleep設true barber開始剪的時候 會把自己設定成not sleep 
	AtomicBoolean barberOnWork;///barber開始剪的時候 會把自己設定成work 剪完的時候 由customer設成not work
	
	//要分sleep跟work是因為要讓customer判斷barber本來有沒有在睡覺
	//barber剛剪完上個客人如果WR還有人不會設成sleep但是 barber thread在pillow還是會停下來 
	//等customer release才會繼續執行 所以要由customer來判斷barber原來有沒有在睡

	BarberShop barbershop;

	int number;

	public Barber(Semaphore pillow, Semaphore seatBelt, AtomicBoolean barberSleep, AtomicBoolean barberOnWork,
			BarberShop barbershop, int number) {
		super();
		this.pillow = pillow;
		this.seatBelt = seatBelt;
		this.barberSleep = barberSleep;
		this.barberOnWork = barberOnWork;
		this.barbershop = barbershop;
		this.number = number;

	}

	@Override
	public void run() {
		while (!barbershop.Alldone.get()) {
			if (barbershop.waitingSeatsAvailable.get() == barbershop.waitingChairs.get()) {/// 剩餘空位等於全部空位 代表沒客人
				barberSleep.getAndSet(true);
				barberOnWork.getAndSet(false);
				System.out.println("Barber" + number + " start sleeping at " + barbershop.getCurrentTime());
			}
			try {
				/// 程式運作上 不論有無客人barber都先睡 等customer release
				pillow.acquire();//pillow==0 就會開始睡 等待有客人來的時候 release pillow 才會繼續執行

				barberSleep.getAndSet(false);
				barberOnWork.getAndSet(true);
				System.out.println("Barber" + number + " start cutting hair at " + barbershop.getCurrentTime());
				Thread.sleep((int) (Math.random() * 3000));
				System.out.println("Barber" + number + " finish cutting hair at " + barbershop.getCurrentTime());
				seatBelt.release();

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

	}

}
