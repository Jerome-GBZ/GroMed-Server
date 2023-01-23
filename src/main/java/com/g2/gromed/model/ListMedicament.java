package com.g2.gromed.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name="ListMedicament")
@AllArgsConstructor
public class ListMedicament {
	
	private String codeCIP7;
	private String codeCIS;
	private String denomination;
	
}
class ResultListMedicament extends Result<ListMedicament>{ }
