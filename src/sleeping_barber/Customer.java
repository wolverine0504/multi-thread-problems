package sleeping_barber;


public class Customer implements Runnable {

	int number;
	BarberShop barbershop;
	Barber[] barbers;
	int barberNumber;

	public Customer(BarberShop barbershop, int number, Barber[] barbers) {
		this.number = number;
		this.barbershop = barbershop;
		this.barbers = barbers;
	}

	@Override
	public void run() {
		//System.out.println("理髮師們 "+barbers);
		try {
			/// 讓顧客隨機出現
			Thread.sleep((int) (Math.random() * 3000));
			///System.out.println(barbershop);
			System.out.println("Customer" + number + " has arrived at " + barbershop.getCurrentTime());

			if (barbershop.waitingSeatsAvailable.get() == 0) {
				System.out.println("There is no availible seat,so " + "Customer" + number + " has leaved at "
						+ barbershop.getCurrentTime());
			} else {
				//System.out.println();
				//要求進入等候室，有空位出來才能進去
				barbershop.waitingRoom.acquire();
				barbershop.waitingSeatsAvailable.decrementAndGet();
				System.out.println("Customer" + number + " has enter waiting room at " + barbershop.getCurrentTime());

				/// 程式運作上 不論有無客人barber都先睡 等customer release
				//// 再用sleep來判斷他本來是不是再睡覺 再決定是不是要叫醒 還是叫他剪就好

				///要求進入理髮室，有空位出來才能進去
				barbershop.barberChair.acquire();/// 開始等有人剪完理髮師有空位
				barberNumber = pickBarber();
				
				////離開等候室，釋放資源給在等待進入的人
				barbershop.waitingRoom.release();/// 叫醒等著進waitingroom的人
				barbershop.waitingSeatsAvailable.getAndIncrement();

				if (barbers[barberNumber].barberSleep.get()) {/// barber本來在睡

					System.out.println("Customer" + number + " wakes barber" + barbers[barberNumber].number + " up at "
							+ barbershop.getCurrentTime());
				} else {//// barber本來沒在睡
					System.out.println("Customer" + number + " is barber" + barbers[barberNumber].number
							+ "'s next customer ,at" + barbershop.getCurrentTime());
				}

				barbers[barberNumber].pillow.release();/// 叫醒barber的thread
				
				/////緊接著扣上seatBelt 開始坐在椅子上給barber剪 
				barbers[barberNumber].seatBelt.acquire();// 開始等理髮師剪完 release seatBelt 代表剪完才可以繼續下去

				// 剪完後把理髮師改成沒在工作
				barbers[barberNumber].barberOnWork.getAndSet(false);
				System.out.println("Customer" + number + " has finished his hair cut and leaved with a big smile at "
						+ barbershop.getCurrentTime());
				
				////釋放出理髮室資源 讓排隊的人可以進去
				barbershop.barberChair.release();/// 叫醒其他在等候室的人

			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int pickBarber() {
		//選擇一個理髮師
		//System.out.println("理髮師："+barbers.length);
		int j;
		for (j = 0; j < barbers.length; j++) {
			//System.out.println(barbers[j]);
			//barberOnWork==false
			if (barbers[j].barberOnWork.get() == false) {/// 找沒有在工作的理髮師 有可能在睡 也有可能沒在睡
				break;
			}
		}
		return j;
	}

}
