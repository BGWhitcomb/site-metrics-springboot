package site.railcartracker.site_metrics.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
// Handles logic of bad ordered inbound inspections; repairs that can't be performed that day
public class BadOrderedRailcar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long badOrderId; // Primary key, auto-incremented
	private String carMark;
	private Integer carNumber;
	private LocalDate badOrderDate; // Populated when user selects isBadOrdered true, inspectedDate becomes
									// badOrderDate
	private String badOrderDescription; // Short user description on why the car is bad ordered
	private LocalDate repairedDate; // Nullable, will remove from active bad order list when this date is populated,
	// will still show in InboundRailcar table when true or false only used for bad
	// orders;
	@JsonProperty("isActive") // set is prefix for front end data handling
	@Builder.Default
	private boolean isActive = true; // Checked for active status by repairedDate; if repairedDate is null, it is
										// true

	@OneToOne
	@JoinColumn(name = "inbound_id", nullable = false)
	@JsonBackReference
	private InboundRailcar inboundRailcar;

	@PrePersist
	@PreUpdate
	private void updateIsActive() {
		this.isActive = (this.repairedDate == null);
	}

	public Long getInboundId() {
		return inboundRailcar != null ? inboundRailcar.getInboundId() : null;
	}

	public Long getBadOrderId() {
		return badOrderId;
	}

	public void setBadOrderId(Long badOrderId) {
		this.badOrderId = badOrderId;
	}

	public String getCarMark() {
		return carMark;
	}

	public void setCarMark(String carMark) {
		this.carMark = carMark;
	}

	public int getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(Integer carNumber) {
		this.carNumber = carNumber;
	}

	public LocalDate getBadOrderDate() {
		return badOrderDate;
	}

	public void setBadOrderDate(LocalDate badOrderDate) {
		this.badOrderDate = badOrderDate;
	}

	public String getBadOrderDescription() {
		return badOrderDescription;
	}

	public void setBadOrderDescription(String badOrderReason) {
		this.badOrderDescription = badOrderReason;
	}

	public LocalDate getRepairedDate() {
		return repairedDate;
	}

	public void setRepairedDate(LocalDate repairedDate) {
		this.repairedDate = repairedDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public InboundRailcar getInboundRailcar() {
		return inboundRailcar;
	}

	public void setInboundRailcar(InboundRailcar inboundRailcar) {
		this.inboundRailcar = inboundRailcar;

	}

}
