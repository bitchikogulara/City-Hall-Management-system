package controller;

import model.CityHall;
import view.SearchByIDForm;
import view.SearchByNameForm;
import view.SearchCitizenMenu;

public class SearchCitizenMenuController {
    private final CityHall cityHall;
    private final SearchCitizenMenu view;

    public SearchCitizenMenuController(CityHall cityHall, SearchCitizenMenu view) {
        this.cityHall = cityHall;
        this.view = view;
    }

    public void openSearchById() {
        new SearchByIDForm(cityHall);
        view.closeWindow();
    }

    public void openSearchByName() {
        new SearchByNameForm(cityHall);
        view.closeWindow();
    }
}
