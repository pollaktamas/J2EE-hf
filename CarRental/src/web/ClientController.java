package web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

import dal.ClientFacade;
import entity.Client;

@Named("clientController")
@SessionScoped
public class ClientController implements Serializable{
	private Client currentClient;
	private DataModel<Client> clientItems = null;
	private boolean editing;

	@EJB
	private ClientFacade clientFacade;

	public Client getCurrent() {
		if (currentClient == null)
			currentClient = new Client();
		return currentClient;
	}

	public void setCurrent(Client current) {
		this.currentClient = current;
	}

	public DataModel<Client> getItems() {
		if (clientItems == null)
			clientItems = new ListDataModel<Client>(clientFacade.findAll());
		return clientItems;
	}

	public void setItems(DataModel<Client> items) {
		this.clientItems = items;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public String prepareList() {
		clientItems = null;
		return FacesUtil.pageWithRedirect("list.xhtml");
	}

	public String prepareNew() {
		editing = false;
		currentClient = null;
		return FacesUtil.pageWithRedirect("edit.xhtml");
	}

	public String prepareEdit() {
		editing = true;
		currentClient = getItems().getRowData();
		return FacesUtil.pageWithRedirect("edit.xhtml");
	}

	public String remove() {
		clientFacade.remove(getItems().getRowData());

		FacesUtil.addInfoMessage("Client successfully removed");

		clientItems = new ListDataModel<Client>(clientFacade.findAll());
		return FacesUtil.pageWithRedirect("list.xhtml");
	}

	public String save() {
		if (editing) {
			clientFacade.edit(currentClient);
			FacesUtil.addInfoMessage("Client successfully edited");
		} else {
			clientFacade.create(currentClient);
			FacesUtil.addInfoMessage("Client successfully created");
		}

		clientItems = null;
		currentClient = null;

		return FacesUtil.pageWithRedirect("list.xhtml");
	}
}
