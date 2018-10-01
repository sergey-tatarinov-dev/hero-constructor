package com.constructor.hero.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Superpower")
@Entity(name = "Superpower")
public class SuperPower {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_superpower")
	private Long id;

	@Basic
	@Column(name = "name")
	private String name;

	@Basic
	@Column(name = "description")
	private String description;
}


