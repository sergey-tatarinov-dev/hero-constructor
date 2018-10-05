package com.constructor.hero.app.service;

import com.constructor.hero.app.entity.Hero;
import com.constructor.hero.app.entity.SuperPower;
import com.constructor.hero.app.repository.HeroRepository;
import com.constructor.hero.app.repository.SuperPowerRepository;
import com.vaadin.ui.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public void save(Hero hero, List<ComboBox<String>> list) {
		Set<SuperPower> superPowers = getSuperPowersFromUIComboBoxes(list);
		hero.setSuperPowers(superPowers);
		heroRepository.save(hero);
	}

	private Set<SuperPower> getSuperPowersFromUIComboBoxes(List<ComboBox<String>> list) {
		Set<SuperPower> superPowers = new HashSet<>();
		if (list != null) {
			list.forEach(comboBox -> {
				if (comboBox.getValue() != null) {
					superPowerRepository.findAll().stream()
							.filter(superPowerName -> comboBox.getValue().equals(superPowerName.getName()))
							.findAny().ifPresent(superPowers::add);
				}
			});
		}
		return superPowers;
	}

	public List<Hero> getAll() {
		return heroRepository.findAll();
	}

	@Transactional
	public void remove(Hero hero) {
		heroRepository.delete(hero);
	}
}
