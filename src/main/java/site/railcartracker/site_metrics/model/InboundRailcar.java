package site.railcartracker.site_metrics.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;

//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//@Getter
@Entity
@Builder
@Component
// Handles logic of inbound inspection of railcars
public class InboundRailcar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer inboundId; // Primary key, auto-incremented
	private String carMark;
	private Integer carNumber;
	private boolean isRepaired; // Running repair performed on inspection, not related to the bad order model
	private LocalDate inspectedDate;
	private boolean isBadOrdered = false; // If true, triggers creation of BadOrderedRailcar, using 1:1 relation with
											// foreign key
	@OneToOne (mappedBy = "inboundRailcar", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private BadOrderedRailcar badOrderedRailcar;

	
	
	public Integer getInboundId() {
		return inboundId;
	}

	public void setInboundId(int inboundId) {
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

	public void setCarNumber(int carNumber) {
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
	
	

}
