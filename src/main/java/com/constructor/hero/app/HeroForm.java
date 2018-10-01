package com.constructor.hero.app;

import com.constructor.hero.app.entity.Hero;
import com.constructor.hero.app.entity.SuperPower;
import com.constructor.hero.app.service.HeroService;
import com.constructor.hero.app.service.SuperPowerService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

@SpringUI
public class HeroForm extends FormLayout {

	private Image heroImage = new Image();

	private TextField name = new TextField("Name");
	private TextField description = new TextField("Description");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private CssLayout superPowers = new CssLayout();

	private HeroService heroService;
	private SuperPowerService superPowerService;
	private Hero hero;
	private GridUI gridUI;
	private Binder<Hero> binder = new Binder<>(Hero.class);

	@Autowired
	public HeroForm(GridUI gridUI, HeroService heroService, SuperPowerService superPowerService) {
		this.gridUI = gridUI;
		this.heroService = heroService;
		this.superPowerService = superPowerService;
		setSizeUndefined();

		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		addComponents(heroImage, name, description, buttons);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		binder.bindInstanceFields(this);

		//superpowers.setItems(hero.getSuperPowerList().stream().map(SuperPower::getName).collect(Collectors.toList()));
		superPowers.setCaption("Superpowers");

		save.addClickListener(event -> save());
		delete.addClickListener(event -> delete());
	}

	public void setHero(Hero hero) {
		this.hero = hero;
		binder.setBean(hero);

		heroImage.setIcon(new FileResource(createFileFromEntity(hero)));
		heroImage.setDescription(hero.getDescription());
		heroImage.setVisible(true);

		superPowers.removeAllComponents();

		hero.getSuperPowerList().forEach(superpower -> {
			Image image = new Image();
			image.setIcon(new FileResource(createFileFromEntity(superpower)));
			image.setDescription(superpower.getName() + "\n\n" + superpower.getDescription());
			image.setVisible(true);

			superPowers.addComponent(image);
		});

		addComponent(superPowers);

		delete.setVisible(hero.isPersisted());
		setVisible(true);
		name.selectAll();
	}

	private File createFileFromEntity(Object entity) {
		StringBuilder imagePath = new StringBuilder(System.getProperty("user.dir"));
		if (entity instanceof Hero) {
			Hero hero = (Hero) entity;
			imagePath.append("/src/main/resources/images/heroes/");
			imagePath.append(hero.getId());
		} else if (entity instanceof SuperPower) {
			SuperPower superPower = (SuperPower) entity;
			imagePath.append("/src/main/resources/images/superpowers/");
			imagePath.append(superPower.getId());
		}
		imagePath.append(".gif");
		return new File(String.valueOf(imagePath));
	}

	private void delete() {
		heroService.remove(hero);
		gridUI.updateList();
	}

	private void save() {
		heroService.save(hero);
		gridUI.updateList();
	}

	public void isVisibleImage(boolean isVisible) {
		heroImage.setVisible(isVisible);
	}
}
