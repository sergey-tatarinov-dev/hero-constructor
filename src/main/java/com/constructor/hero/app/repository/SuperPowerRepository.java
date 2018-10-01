package com.constructor.hero.app.repository;

import com.constructor.hero.app.entity.SuperPower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperPowerRepository extends JpaRepository<SuperPower, Long> {
}
