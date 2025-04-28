package site.railcartracker.site_metrics.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;

//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//@Getter
@Entity
@Builder
@Component
// Handles logic of bad ordered inbound inspections; repairs that can't be performed that day
public class BadOrderedRailcar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer badOrderId; // Primary key, auto-incremented
	private String carMark;
	private Integer carNumber;
	private LocalDate badOrderDate; // Populated when user selects isBadOrdered true, inspectedDate becomes
									// badOrderDate
	private String badOrderReason; // Short user description on why the car is bad ordered
	private LocalDate repairedDate; // Nullable, will remove from active bad order list when this date is populated,
									// will still show in InboundRailcar table
	private boolean isActive = true; // Checked for active status by repairedDate; if repairedDate is null, it is
										// true
	@OneToOne // there is only one bad order instance to one inspection instance ever
	@JoinColumn(name = "inboundId", referencedColumnName = "inboundId", nullable = false) // Bad order can't exist without an instance of InboundRailcar
	@JsonBackReference
	private InboundRailcar inboundRailcar;

	public BadOrderedRailcar(Integer badOrderId, String carMark, Integer carNumber, LocalDate badOrderDate,
			String badOrderReason, LocalDate repairedDate, boolean isActive, InboundRailcar inboundRailcar) {
		super();
		this.badOrderId = badOrderId;
		this.carMark = carMark;
		this.carNumber = carNumber;
		this.badOrderDate = badOrderDate;
		this.badOrderReason = badOrderReason;
		this.repairedDate = repairedDate;
		this.isActive = isActive;
		this.inboundRailcar = inboundRailcar;
	}

	public BadOrderedRailcar() {
	}

	public int getBadOrderId() {
		return badOrderId;
	}

	public void setBadOrderId(Integer badOrderId) {
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

	public String getBadOrderReason() {
		return badOrderReason;
	}

	public void setBadOrderReason(String badOrderReason) {
		this.badOrderReason = badOrderReason;
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

	@Override
	public String toString() {
		return "BadOrderedRailcar [badOrderId=" + badOrderId + ", carMark=" + carMark + ", carNumber=" + carNumber
				+ ", badOrderDate=" + badOrderDate + ", badOrderReason=" + badOrderReason + ", repairedDate="
				+ repairedDate + ", isActive=" + isActive + ", inboundRailcar=" + inboundRailcar + "]";
	}

}
