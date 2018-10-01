package com.constructor.hero.app;

import com.constructor.hero.app.entity.Hero;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class HeroLayout extends HorizontalLayout {

	private TextField name;
	private TextField description;

	public HeroLayout(Hero hero, HeroChangeListener listener) {
		/*setSpacing(true);
		setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		name = new TextField("name");
		description = new TextField("description");
		Binder<Hero> binder = new Binder<>();
		binder.forField(name)
				.bind(Hero::getName, Hero::setName);
		binder.forField(description)
				.bind(Hero::getDescription, Hero::setDescription);
		binder.setBean(hero);
		addComponents(name, description);
		setExpandRatio(name, 1);
		Arrays.asList(name, description).forEach(field -> {
			field.addValueChangeListener(change -> {
				listener.heroChanged(hero);
				field.focus();
			});
			field.setValueChangeMode(ValueChangeMode.LAZY);
		});*/
	}
}
