package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CONDITION_DELIVRANCE")
public class ConditionDelivrance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idConditionDelivrance;

	@Column(length = 1024)
	private String condition;
	
	
	
	
}
