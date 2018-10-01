package com.constructor.hero.app.service;

import com.constructor.hero.app.HeroChangeListener;
import com.constructor.hero.app.HeroLayout;
import com.constructor.hero.app.entity.Hero;
import com.constructor.hero.app.entity.SuperPower;
import com.constructor.hero.app.repository.HeroRepository;
import com.constructor.hero.app.repository.SuperPowerRepository;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HeroService extends VerticalLayout implements HeroChangeListener {

	private final HeroRepository heroRepository;
	private final SuperPowerRepository superPowerRepository;

	@Autowired
	public HeroService(HeroRepository heroRepository, SuperPowerRepository superPowerRepository) {
		this.heroRepository = heroRepository;
		this.superPowerRepository = superPowerRepository;
	}

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

	public void save(Hero hero, List<ComboBox<String>> list) {
		if (list != null) {
			Set<SuperPower> superPowers = new HashSet<>();
			list.forEach(comboBox -> {
				superPowerRepository.findAll().stream()
						.filter(superPowerName -> comboBox.getValue().equals(superPowerName.getName()))
						.findAny().ifPresent(superPowers::add);
			});
			hero.setSuperPowers(superPowers);
		}
		heroRepository.saveAndFlush(hero);
		update();
	}

	@Override
	public void heroChanged(Hero hero) {
		save(hero, null);
	}

	public List<Hero> getAll() {
		return heroRepository.findAll();
	}

	public void remove(Object item) {
		Hero hero = (Hero) item;
		heroRepository.delete(hero);
	}
}
