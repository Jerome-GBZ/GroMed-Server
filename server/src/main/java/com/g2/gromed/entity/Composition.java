package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMPOSITION",indexes = {
		@Index(name="denominationSubstanceIndex", columnList = "denominationSubstance"),
})
public class Composition {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idComposition;

	private String codeSubstance;

	@Column(length = 1024)
	private String denominationSubstance;

	@Column(length = 1024)
	private String designationElementPharmaceutique;

	private String dosage;

	@Column(length = 1024)
	private String referenceDosage;

	@Column(length = 1024)
	private String natureComposant;
}
