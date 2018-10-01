package com.constructor.hero.app.service;

import com.constructor.hero.app.SuperPowerLayout;
import com.constructor.hero.app.entity.SuperPower;
import com.constructor.hero.app.repository.SuperPowerRepository;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class SuperPowerService extends VerticalLayout {

	private final SuperPowerRepository superPowerRepository;

	@Autowired
	public SuperPowerService(SuperPowerRepository superPowerRepository) {
		this.superPowerRepository = superPowerRepository;
	}

	@PostConstruct
	void init() {
		setSpacing(true);
		update();
	}

	private void update() {
		setSuperPowers(superPowerRepository.findAll());
	}

	private void setSuperPowers(List<SuperPower> superPowers) {
		removeAllComponents();
		superPowers.forEach(superPower -> {
			addComponent(new SuperPowerLayout(superPower, this));
		});
	}

	public void save(SuperPower superPower) {
		superPowerRepository.save(superPower);
		update();
	}

	public List<SuperPower> getAll() {
		return superPowerRepository.findAll();
	}

	public void remove(Object item) {
		SuperPower superPower = (SuperPower) item;
		superPowerRepository.delete(superPower);
	}

}
