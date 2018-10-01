package com.constructor.hero.app;

import com.constructor.hero.app.entity.Hero;
import com.constructor.hero.app.service.HeroService;
import com.constructor.hero.app.service.SuperPowerService;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringUI
@Theme("valo")
public class GridUI extends UI {

	@Autowired
	private HeroService heroService;
	@Autowired
	private SuperPowerService superPowerService;
	private VerticalLayout layout;
	private HorizontalLayout content;
	private HeroForm heroForm;
	private TextField filterText;
	private Grid<Hero> grid;

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
		filterText = new TextField();
		layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		setContent(layout);
	}

	private void addForm() {
		VerticalLayout formLayout = new VerticalLayout();
		formLayout.setSpacing(true);
		formLayout.setWidth("80%");

		filterText.setPlaceholder("filter by name...");
		filterText.addValueChangeListener(event -> updateList());
		filterText.setValueChangeMode(ValueChangeMode.LAZY);

		Button clearFilterTextButton = new Button(VaadinIcons.CLOSE);
		clearFilterTextButton.setDescription("Clear the current filter");
		clearFilterTextButton.addClickListener(event -> filterText.clear());

		CssLayout filtering = new CssLayout();
		filtering.addComponents(filterText, clearFilterTextButton);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		Button addHeroButton = new Button("Add new hero");
		addHeroButton.addClickListener(event -> {
			grid.asSingleSelect().clear();
			heroForm.setHero(new Hero());
			heroForm.isVisibleImage(false);
		});
		HorizontalLayout toolbar = new HorizontalLayout(filtering, addHeroButton);

		grid.addColumn(Hero::getName).setCaption("Name");
		grid.addColumn(Hero::getDescription).setCaption("Description");

		updateList();

		HorizontalLayout main = new HorizontalLayout(grid, heroForm);
		main.setSizeFull();
		grid.setSizeFull();
		main.setExpandRatio(grid, 1);

		formLayout.addComponents(toolbar, main);
		formLayout.setSizeFull();

		heroForm.setVisible(false);

		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() == null) {
				heroForm.setVisible(true);
			} else {
				heroForm.setHero(event.getValue());
				heroForm.isVisibleImage(true);
			}
		});

		layout.addComponent(formLayout);
	}

	private void addHeroList() {
		heroService.setWidth("80%");
		layout.addComponent(heroService);
	}

	protected void updateList() {
		List<Hero> heroes = heroService.getAll();
		grid.setItems(heroes);
	}

}
