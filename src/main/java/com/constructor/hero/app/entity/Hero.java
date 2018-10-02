package com.constructor.hero.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "Hero")
@Table(name = "Hero")
public class Hero {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_hero")
	private Long id;

	@Basic
	@Column(name = "name")
	private String name;

	@Basic
	@Column(name = "description")
	private String description;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "Hero_Superpower",
			joinColumns = @JoinColumn(name = "id_hero"),
			inverseJoinColumns = @JoinColumn(name = "id_superpower")
	)
	private Set<SuperPower> superPowers = new HashSet<>();

	public Hero() {
	}

	public boolean isPersisted() {
		return id != null;
	}
}

