package web;

public class RentData {
	private String clientName;
	private int carId;
	private boolean rentFulfilled;
	private String startDate;
	private String deadLineDate;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public boolean isRentFulfilled() {
		return rentFulfilled;
	}

	public void setRentFulfilled(boolean rentFulfilled) {
		this.rentFulfilled = rentFulfilled;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDeadLineDate() {
		return deadLineDate;
	}

	public void setDeadLineDate(String deadLineDate) {
		this.deadLineDate = deadLineDate;
	}
}
