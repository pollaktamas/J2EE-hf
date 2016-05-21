package dal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.Car;
import entity.Client;
import entity.Rent;

@Stateless
@LocalBean
@DeclareRoles({ Client.USER_ROLE, Client.ADMIN_ROLE })
public class CarFacade extends AbstractFacade<Car> {

	@PersistenceContext
	private EntityManager em;

	public CarFacade() {
		super(Car.class);
	}

	@Override
	protected EntityManager em() {
		return em;
	}

	@RolesAllowed({Client.USER_ROLE, Client.ADMIN_ROLE})
	@Override
	public Car find(Object id) {
		return super.find(id);
	}

	@Override
	@PermitAll
	public List<Car> findAll() {
		return super.findAll();
	}

	@RolesAllowed({Client.USER_ROLE, Client.ADMIN_ROLE})
	@Override
	public List<Car> findRange(int[] range) {
		return super.findRange(range);
	}

	@RolesAllowed({Client.USER_ROLE, Client.ADMIN_ROLE})
	@Override
	public int count() {
		return super.count();
	}
	
	@PermitAll
	public List<Car> search(String manufacturer, String modelType, boolean available, List<Rent> rents) {		
		String queryString = "Select c FROM Car c WHERE c.manufacturer LIKE :manufacturer AND c.modeltype LIKE :modeltype";
		TypedQuery<Car> jpqlQuery = em.createQuery(queryString, Car.class);
		
		jpqlQuery.setParameter("manufacturer", "%" + manufacturer + "%");
		jpqlQuery.setParameter("modeltype", "%" + modelType + "%");

		List<Car> results = jpqlQuery.getResultList();
		
		List<Car> finalResult = new ArrayList<Car>();
		
		// Check if car is available
		if (!results.isEmpty()) {
			for (Car car : results) {
				if (isCarAvailable(rents, car.getCarId()) == available) {
					finalResult.add(car);
				}
			}			
		}
				
		return finalResult;
	}	

	@Override
	@RolesAllowed({Client.ADMIN_ROLE})
	public void create(Car entity) {
		super.create(entity);
	}

	@Override
	@RolesAllowed({Client.ADMIN_ROLE})
	public void edit(Car entity) {
		super.edit(entity);
	}

	@Override
	@RolesAllowed({Client.ADMIN_ROLE})
	public void remove(Car entity) {
		super.remove(entity);
	}
	
	private boolean isCarAvailable(List<Rent> rents, int carId) {
		for(Rent rent : rents) {
			if (rent.getCar().getCarId() == carId) {
				if (rent.getRentFulfilled() == 0) {
					return false;
				}
			}
		}		
		return true;
	}
}