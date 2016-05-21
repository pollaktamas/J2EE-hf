package web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("editPageController")
@SessionScoped
public class EditPageController implements Serializable {
	private String entityToEdit;

	public String getEntityToEdit() {
		return entityToEdit;
	}

	public void setEntityToEdit(String entityToEdit) {
		this.entityToEdit = entityToEdit;
	}
	
	public void setEntityString(String entityToEdit) {
		this.entityToEdit = entityToEdit;
	}
}
