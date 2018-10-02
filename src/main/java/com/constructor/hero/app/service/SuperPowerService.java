package com.constructor.hero.app.service;

import com.constructor.hero.app.entity.SuperPower;
import com.constructor.hero.app.repository.SuperPowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperPowerService {

	private final SuperPowerRepository superPowerRepository;

	@Autowired
	public SuperPowerService(SuperPowerRepository superPowerRepository) {
		this.superPowerRepository = superPowerRepository;
	}

	public void save(SuperPower superPower) {
		superPowerRepository.save(superPower);
	}

	public List<SuperPower> getAll() {
		return superPowerRepository.findAll();
	}

	public void remove(Object item) {
		SuperPower superPower = (SuperPower) item;
		superPowerRepository.delete(superPower);
	}

}
