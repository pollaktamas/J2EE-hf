package dal;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.Client;
import entity.Rent;

@Stateless
@LocalBean
@DeclareRoles({ Client.USER_ROLE, Client.ADMIN_ROLE })
public class RentFacade extends AbstractFacade<Rent> {

	@PersistenceContext
	private EntityManager em;

	public RentFacade() {
		super(Rent.class);
	}

	@Override
	protected EntityManager em() {
		return em;
	}

	@RolesAllowed({Client.USER_ROLE, Client.ADMIN_ROLE})
	@Override
	public Rent find(Object id) {
		return super.find(id);
	}

	@Override
	@PermitAll
	public List<Rent> findAll() {
		return super.findAll();
	}

	@RolesAllowed({Client.USER_ROLE, Client.ADMIN_ROLE})
	@Override
	public List<Rent> findRange(int[] range) {
		return super.findRange(range);
	}
	
	@RolesAllowed({Client.USER_ROLE, Client.ADMIN_ROLE})
	public List<Rent> findRentsOfClient(String clientName) {	
		String queryString = "FROM Rent R WHERE R.client.name = :client_id";
		TypedQuery<Rent> query = em.createQuery(queryString, Rent.class);
		
		query.setParameter("client_id", clientName);
		List<Rent> results = query.getResultList();
		
		return results;
	}

	@RolesAllowed({Client.USER_ROLE, Client.ADMIN_ROLE})
	@Override
	public int count() {
		return super.count();
	}

	@Override
	@RolesAllowed({Client.ADMIN_ROLE})
	public void create(Rent entity) {
		super.create(entity);
	}

	@Override
	@RolesAllowed({Client.ADMIN_ROLE})
	public void edit(Rent entity) {
		super.edit(entity);
	}

	@Override
	@RolesAllowed({Client.ADMIN_ROLE})
	public void remove(Rent entity) {
		super.remove(entity);
	}	
}