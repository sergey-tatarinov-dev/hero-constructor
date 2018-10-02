package com.constructor.hero.app.service;

import com.constructor.hero.app.entity.Hero;
import com.constructor.hero.app.entity.SuperPower;
import com.constructor.hero.app.repository.HeroRepository;
import com.constructor.hero.app.repository.SuperPowerRepository;
import com.vaadin.ui.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HeroService {

	private final HeroRepository heroRepository;
	private final SuperPowerRepository superPowerRepository;

	@Autowired
	public HeroService(HeroRepository heroRepository, SuperPowerRepository superPowerRepository) {
		this.heroRepository = heroRepository;
		this.superPowerRepository = superPowerRepository;
	}

	public void save(Hero hero, List<ComboBox<String>> list) {
		if (list != null) {
			Set<SuperPower> superPowers = new HashSet<>();
			list.forEach(comboBox -> {
				if (comboBox.getValue() != null) {
					superPowerRepository.findAll().stream()
							.filter(superPowerName -> comboBox.getValue().equals(superPowerName.getName()))
							.findAny().ifPresent(superPowers::add);
				}
			});
			hero.setSuperPowers(superPowers);
		}
		heroRepository.saveAndFlush(hero);
	}

	public List<Hero> getAll() {
		return heroRepository.findAll();
	}

	public void remove(Object item) {
		Hero hero = (Hero) item;
		heroRepository.delete(hero);
	}
}
