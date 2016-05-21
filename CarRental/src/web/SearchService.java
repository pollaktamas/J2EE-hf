package web;

import java.util.List;

import javax.jws.WebService;

import entity.Car;

@WebService
public interface SearchService {
	public List<Car> searchCarsWebMethod(String manufacturer, String modelType, boolean available);
}
