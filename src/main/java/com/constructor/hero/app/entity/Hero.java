package com.constructor.hero.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "Hero_Superpower",
			joinColumns = @JoinColumn(name = "id_hero"),
			inverseJoinColumns = @JoinColumn(name = "id_superpower")
	)
	private List<SuperPower> superPowerList = new ArrayList<>();

	public Hero() {
	}

	public Hero(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public boolean isPersisted() {
		return id != null;
	}
}
