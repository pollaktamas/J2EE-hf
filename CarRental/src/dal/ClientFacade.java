package dal;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entity.Client;

@Stateless
@LocalBean
@DeclareRoles({ Client.USER_ROLE, Client.ADMIN_ROLE })
public class ClientFacade extends AbstractFacade<Client> {

	@PersistenceContext
	private EntityManager em;

	public ClientFacade() {
		super(Client.class);
	}

	@Override
	protected EntityManager em() {
		return em;
	}

	@RolesAllowed({Client.USER_ROLE, Client.ADMIN_ROLE})
	@Override
	public Client find(Object id) {
		return super.find(id);
	}

	@RolesAllowed({Client.USER_ROLE, Client.ADMIN_ROLE})
	@Override
	public List<Client> findAll() {
		return super.findAll();
	}

	@RolesAllowed({Client.USER_ROLE, Client.ADMIN_ROLE})
	@Override
	public List<Client> findRange(int[] range) {
		return super.findRange(range);
	}

	@RolesAllowed({Client.USER_ROLE, Client.ADMIN_ROLE})
	@Override
	public int count() {
		return super.count();
	}

	@Override
	@RolesAllowed({Client.ADMIN_ROLE})
	public void create(Client entity) {
		super.create(entity);
	}

	@Override
	@RolesAllowed({Client.ADMIN_ROLE})
	public void edit(Client entity) {
		super.edit(entity);
	}

	@Override
	@RolesAllowed({Client.ADMIN_ROLE})
	public void remove(Client entity) {
		super.remove(entity);
	}	
}