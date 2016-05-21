package web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

import dal.CarFacade;
import dal.RentFacade;
import entity.Car;

@Named("carController")
@SessionScoped
public class CarController implements Serializable{
	private Car currentCar;
	private DataModel<Car> carItems = null;
	private boolean editing;

	// Storing search parameters
	private CarSearchParameters carSearchParameters;
	
	@EJB
	private CarFacade carFacade;
	
	@EJB
	private RentFacade rentFacade;

	public Car getCurrent() {
		if (currentCar == null)
			currentCar = new Car();
		return currentCar;
	}

	public void setCurrent(Car current) {
		this.currentCar = current;
	}

	public DataModel<Car> getItems() {
		if (carItems == null)
			carItems = new ListDataModel<Car>(carFacade.findAll());
		return carItems;
	}

	public void setItems(DataModel<Car> items) {
		this.carItems = items;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public CarSearchParameters getCarSearchParameters() {
		if (carSearchParameters == null)
			carSearchParameters = new CarSearchParameters();
		return carSearchParameters;
	}

	public void setCarSearchParameters(CarSearchParameters carSearchParameters) {
		this.carSearchParameters = carSearchParameters;
	}
	
	public String prepareList() {
		carItems = null;
		return FacesUtil.pageWithRedirect("list.xhtml");
	}

	public String prepareNew() {
		editing = false;
		currentCar = null;
		return FacesUtil.pageWithRedirect("edit.xhtml");
	}

	public String prepareEdit() {
		editing = true;
		currentCar = getItems().getRowData();
		return FacesUtil.pageWithRedirect("edit.xhtml");
	}

	public String remove() {
		carFacade.remove(getItems().getRowData());

		FacesUtil.addInfoMessage("Car successfully removed");

		carItems = new ListDataModel<Car>(carFacade.findAll());
		return FacesUtil.pageWithRedirect("list.xhtml");
	}

	public String save() {
		if (editing) {
			carFacade.edit(currentCar);			
			FacesUtil.addInfoMessage("Car successfully edited");
		} else {
			carFacade.create(currentCar);
			FacesUtil.addInfoMessage("Car successfully created");
		}

		carItems = null;
		currentCar = null;

		return FacesUtil.pageWithRedirect("list.xhtml");
	}
	
	public void refreshCarList() {
		carItems = new ListDataModel<Car>(carFacade.findAll());
	}
	
	public void searchCars() {	
		carItems = new ListDataModel<Car>(carFacade.search(carSearchParameters.getManufacturer(), carSearchParameters.getModelType(), 
				carSearchParameters.isAvailable(), rentFacade.findAll()));
	}
}
