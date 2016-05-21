package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Rent implements Serializable {
	private static final long serialVersionUID = -6175312004190916045L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int rentId;
	
	@ManyToOne
	@JoinColumn(name="name")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name="carId")
	private Car car;
	
	private int rentFulfilled;
	
	private java.sql.Date rentStartDate;
	
	private java.sql.Date rentDeadlineDate;
	
	public Rent() {
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public int getRentFulfilled() {
		return rentFulfilled;
	}

	public void setRentFulfilled(int rentFulfilled) {
		this.rentFulfilled = rentFulfilled;
	}

	public java.sql.Date getRentStartDate() {
		return rentStartDate;
	}

	public void setRentStartDate(java.sql.Date rentStartDate) {
		this.rentStartDate = rentStartDate;
	}

	public java.sql.Date getRentDeadlineDate() {
		return rentDeadlineDate;
	}

	public void setRentDeadlineDate(java.sql.Date rentDeadlineDate) {
		this.rentDeadlineDate = rentDeadlineDate;
	}
}
