package model.flight.route;

import model.persistence.entity.Airport;
import model.persistence.service.AirportService;
import model.persistence.service.RouteService;
import model.persistence.entity.Route;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Search {

    private List<String> airportsNames =  new ArrayList<>();
    private RouteService routeService = new RouteService();
    private AirportService airportService = new AirportService();
    private Airport airport = new Airport();


    public List<String> getServedDepartures(){

        List<Route> routes = routeService.findAll();
        for (int i = 0; i < routes.size(); i++) {
            airportsNames.add(routes.get(i).getDepartureName());
        }
        airportsNames.sort(Comparator.naturalOrder());
        airportsNames = airportsNames.stream().distinct().collect(Collectors.toList());
        return airportsNames;
    }

    public List<String> getServedArrivals(String servedByDeparture){

        List<Route> routes = routeService.findByDepName(servedByDeparture);
        airport = airportService.findByName(servedByDeparture).get(0);
        for (int i = 0; i < routes.size(); i++) {
            airportsNames.add(routes.get(i).getArrivalName());
        }
        airportsNames.sort(Comparator.naturalOrder());
        airportsNames = airportsNames.stream().distinct().collect(Collectors.toList());
        return airportsNames;
    }

}
