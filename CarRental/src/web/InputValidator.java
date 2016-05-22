package web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import dal.CarFacade;
import dal.ClientFacade;

@Named("inputValidator")
@SessionScoped
public class InputValidator implements Serializable {
	@EJB
	ClientFacade clientFacade;
	
	@EJB
	CarFacade carFacade;
	
	public void validateClientNameExistence(FacesContext context, UIComponent toValidate, Object value) {
		String clientName = (String) value;
		if (clientFacade.find(clientName) == null) {
			((UIInput) toValidate).setValid(false);
			FacesUtil.addInfoMessage("No existing user with this name!");
		}
	}
	
	public void validateCarExistence(FacesContext context, UIComponent toValidate, Object value) {
		Integer carId = (Integer) value;
		if (carFacade.find(carId) == null) {
			((UIInput) toValidate).setValid(false);
			FacesUtil.addInfoMessage("No existing car with this id!");
		}
	}
}
