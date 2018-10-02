package com.constructor.hero.app;

import com.constructor.hero.app.entity.Hero;
import com.constructor.hero.app.service.HeroService;
import com.constructor.hero.app.service.SuperPowerService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringUI
@Theme("valo")
public class GridUI extends UI {

	private final HeroService heroService;
	private final SuperPowerService superPowerService;
	private VerticalLayout layout;
	private HeroForm heroForm;
	private Grid<Hero> grid;

	@Autowired
	public GridUI(HeroService heroService, SuperPowerService superPowerService) {
		this.heroService = heroService;
		this.superPowerService = superPowerService;
	}

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		getPage().setTitle("Constructor of heroes");

		HorizontalLayout content = new HorizontalLayout();
		content.setSpacing(true);
		setContent(content);

		setupLayout();
		addForm();
	}

	private void setupLayout() {
		heroForm = new HeroForm(this, heroService, superPowerService);
		grid = new Grid<>();
		layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		setContent(layout);
	}

	private void addForm() {
		VerticalLayout formLayout = new VerticalLayout();
		formLayout.setSpacing(true);
		formLayout.setWidth("80%");

		grid.addColumn(Hero::getName).setCaption("Name");
		grid.addColumn(Hero::getDescription).setCaption("Description");
		grid.setSizeFull();
		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() == null) {
				heroForm.setVisible(true);
			} else {
				heroForm.setHero(event.getValue());
				heroForm.isVisibleImage(true);
			}
		});
		updateList();

		heroForm.setVisible(false);
		HorizontalLayout main = new HorizontalLayout(grid, heroForm);
		main.setSizeFull();

		Button addHeroButton = new Button("Add new hero");
		addHeroButton.addClickListener(event -> {
			grid.asSingleSelect().clear();
			heroForm.setHero(new Hero());
			heroForm.isVisibleImage(false);
		});

		formLayout.addComponents(addHeroButton, main);
		formLayout.setSizeFull();

		layout.addComponent(formLayout);
	}

	protected void updateList() {
		List<Hero> heroes = heroService.getAll();
		grid.setItems(heroes);
	}

}
