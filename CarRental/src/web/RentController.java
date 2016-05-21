package web;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

import dal.CarFacade;
import dal.ClientFacade;
import dal.RentFacade;
import entity.Rent;

@Named("rentController")
@SessionScoped
public class RentController implements Serializable{
	private Rent currentRent;
	private DataModel<Rent> actualUserRents = null;
	private DataModel<Rent> rentItems = null;
	private boolean editing;
	
	private boolean userRentsShowable;
	
	// Storing rent data need for rent modification or new rent creation
	private RentData rentData;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	
	@EJB
	private RentFacade rentFacade;
	
	@EJB
	private ClientFacade clientFacade;
	
	@EJB
	private CarFacade carFacade;

	public Rent getCurrent() {
		if (currentRent == null)
			currentRent = new Rent();
		return currentRent;
	}

	public void setCurrent(Rent current) {
		this.currentRent = current;
	}

	public DataModel<Rent> getActualUserRents() {		
		if (actualUserRents == null)
			actualUserRents = new ListDataModel<Rent>();
		
		return actualUserRents;
	}

	public void setActualUserRents(DataModel<Rent> actualUserRents) {
		this.actualUserRents = actualUserRents;
	}
	
	public DataModel<Rent> getItems() {
		if (rentItems == null)
			rentItems = new ListDataModel<Rent>(rentFacade.findAll());
		return rentItems;
	}

	public void setItems(DataModel<Rent> items) {
		this.rentItems = items;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}
	
	public boolean isUserRentsShowable() {
		return userRentsShowable;
	}

	public void setUserRentsShowable(boolean userRentsShowable) {
		this.userRentsShowable = userRentsShowable;
	}
	
	public RentData getRentData() {
		if (rentData == null)
			rentData = new RentData();
		return rentData;
	}

	public void setRentData(RentData rentData) {
		this.rentData = rentData;
	}
	
	public String prepareList() {
		rentItems = null;
		return FacesUtil.pageWithRedirect("list.xhtml");
	}

	public String prepareNew() {
		editing = false;
		currentRent = null;
		rentData = null;
		return FacesUtil.pageWithRedirect("edit.xhtml");
	}

	public String prepareEdit() {
		editing = true;
		currentRent = getItems().getRowData();		
		setEditFormData();	
		return FacesUtil.pageWithRedirect("edit.xhtml");
	}

	public String remove() {
		rentFacade.remove(getItems().getRowData());

		FacesUtil.addInfoMessage("Rent successfully removed");

		rentItems = new ListDataModel<Rent>(rentFacade.findAll());
		return FacesUtil.pageWithRedirect("list.xhtml");
	}

	public String save() {
		if (editing) {
			gatherCurrentRentData();
			rentFacade.edit(currentRent);

			FacesUtil.addInfoMessage("Rent successfully edited");
		} else {
			gatherCurrentRentData();
			rentFacade.create(currentRent);
		
			FacesUtil.addInfoMessage("Rent successfully created");
		}

		rentItems = null;
		currentRent = null;

		return FacesUtil.pageWithRedirect("list.xhtml");
	}
	
	public void showRentHistory(String actualUserId) {
		actualUserRents = new ListDataModel<Rent>(rentFacade.findRentsOfClient(actualUserId));
	
		userRentsShowable = true;
	}
	
	public String booleanIntToYesNo(int number) {
		if (number == 1) {
			return "YES";
		} else {
			return "NO";
		}
	}
	
	public boolean isCarAvailable(int carId) {
		getItems();

		for(Rent rent : rentItems) {
			if (rent.getCar().getCarId() == carId) {
				if (rent.getRentFulfilled() == 0) {
					return false;
				}
			}
		}
		
		return true;
	}

	// Assemble current rent from RentData object attributes
	private void gatherCurrentRentData() {
		currentRent = getCurrent();
		
		// Date parsing
		try {
			Date rentStartDate = new Date(dateFormat.parse(rentData.getStartDate()).getTime());
			Date rentDeadlineDate = new Date(dateFormat.parse(rentData.getDeadLineDate()).getTime());
			
			currentRent.setRentStartDate(rentStartDate);
			currentRent.setRentDeadlineDate(rentDeadlineDate);
		} catch (ParseException e) {
			System.out.println("Date conversion failed!");
			e.printStackTrace();
		}
		
		// Find client
		currentRent.setClient(clientFacade.find(rentData.getClientName()));
		
		// Find car
		currentRent.setCar(carFacade.find(rentData.getCarId()));
		
		// Set rentFulfilled attribute
		currentRent.setRentFulfilled(rentData.isRentFulfilled() ? 1 : 0);
	}
	
	// Set RentData (which is bind to edit form) parameters from current rent object
	private void setEditFormData() {
		rentData = new RentData();
		
		rentData.setClientName(currentRent.getClient().getName());
		rentData.setCarId(currentRent.getCar().getCarId());
		rentData.setStartDate(currentRent.getRentStartDate().toString());
		rentData.setDeadLineDate(currentRent.getRentDeadlineDate().toString());
		rentData.setRentFulfilled(currentRent.getRentFulfilled() == 1);
	}
}
