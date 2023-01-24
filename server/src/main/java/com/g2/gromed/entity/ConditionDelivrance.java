package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ConditionDelivrance")
public class ConditionDelivrance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idConditionDelivrance;
	@Column(length = 1024)
	private String condition;
	
	
	
	
}
