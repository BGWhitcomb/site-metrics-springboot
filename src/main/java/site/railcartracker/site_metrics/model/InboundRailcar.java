package site.railcartracker.site_metrics.model;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
// Handles logic of inbound inspection of railcars
public class InboundRailcar {
	@Id
	@Column(name = "inbound_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long inboundId; // Primary key, auto-incremented

	private String carMark;
	private Integer carNumber;
	
	@JsonProperty("isRepaired")
	@Builder.Default // defaults to false unless user specifies in front end components
	private boolean isRepaired = false; // Running repair performed on inspection, not related to the bad order model
	private LocalDate inspectedDate;
	
	@JsonProperty("isEmpty")
	@Builder.Default
	private boolean isEmpty = true; // Empty or loaded railcar at the time of inspection, most of the time they are
									// empty
	private String repairDescription;
	
	@Builder.Default
	private boolean isBadOrdered = false; // If true, triggers creation of BadOrderedRailcar, using 1:1 relation with
											// foreign key
	@OneToOne(mappedBy = "inboundRailcar", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private BadOrderedRailcar badOrderedRailcar;

	// Getters and Setters

	public Long getInboundId() {
		return inboundId;
	}

	public void setInboundId(Long inboundId) {
		this.inboundId = inboundId;
	}

	public String getCarMark() {
		return carMark;
	}

	public void setCarMark(String carMark) {
		this.carMark = carMark;
	}

	public Integer getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(Integer carNumber) {
		this.carNumber = carNumber;
	}

	public boolean isRepaired() {
		return isRepaired;
	}

	public void setRepaired(boolean isRepaired) {
		this.isRepaired = isRepaired;
	}

	public LocalDate getInspectedDate() {
		return inspectedDate;
	}

	public void setInspectedDate(LocalDate inspectedDate) {
		this.inspectedDate = inspectedDate;
	}

	public boolean isBadOrdered() {
		return isBadOrdered;
	}

	public void setBadOrdered(boolean isBadOrdered) {
		this.isBadOrdered = isBadOrdered;
	}

	public BadOrderedRailcar getBadOrderedRailcar() {
		return badOrderedRailcar;
	}

	public void setBadOrderedRailcar(BadOrderedRailcar badOrderedRailcar) {
		this.badOrderedRailcar = badOrderedRailcar;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public String getRepairDescription() {
		return repairDescription;
	}

	public void setRepairDescription(String repairDescription) {
		this.repairDescription = repairDescription;
	}

	@Override
	public String toString() {
		return "InboundRailcar [inboundId=" + inboundId + ", carMark=" + carMark + ", carNumber=" + carNumber
				+ ", isRepaired=" + isRepaired + ", inspectedDate=" + inspectedDate + ", isEmpty=" + isEmpty
				+ ", repairDescription=" + repairDescription + ", isBadOrdered=" + isBadOrdered + ", badOrderedRailcar="
				+ badOrderedRailcar + "]";
	}

}