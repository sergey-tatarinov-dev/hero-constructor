package com.constructor.hero.app.service;

import com.constructor.hero.app.HeroChangeListener;
import com.constructor.hero.app.HeroLayout;
import com.constructor.hero.app.entity.Hero;
import com.constructor.hero.app.repository.HeroRepository;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class HeroService extends VerticalLayout implements HeroChangeListener {

	@Autowired
	private HeroRepository heroRepository;

	@PostConstruct
	void init() {
		setSpacing(true);
		update();
	}

	private void update() {
		setHeroes(getAll());
	}

	private void setHeroes(List<Hero> heroes) {
		removeAllComponents();
		heroes.forEach(hero -> {
			addComponent(new HeroLayout(hero, this));
		});
	}

	public void save(Hero hero) {
		heroRepository.save(hero);
		update();
	}

	@Override
	public void heroChanged(Hero hero) {
		save(hero);
	}

	public List<Hero> getAll() {
		return heroRepository.findAll();
	}

	public void remove(Object item) {
		Hero hero = (Hero) item;
		heroRepository.delete(hero);
	}
}
