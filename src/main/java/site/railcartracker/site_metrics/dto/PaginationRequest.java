package site.railcartracker.site_metrics.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class PaginationRequest {

	@Min(value = 0, message = "Page number must be non-negative")
	private int page = 0;

	@Min(value = 1, message = "Page size must be at least 1")
	@Max(value = 100, message = "Page size must not exceed 100")
	private int size = 20;

	private String sortBy = "id"; // Default sort column
	private String sortDirection = "asc"; // asc or desc

	// Filter parameters (optional)
	private String carMark;
	private String startDate;
	private String endDate;

	// Constructors
	public PaginationRequest() {
	}

	public PaginationRequest(int page, int size, String sortBy, String sortDirection) {
		this.page = page;
		this.size = size;
		this.sortBy = sortBy;
		this.sortDirection = sortDirection;
	}

	// Getters and setters
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getCarMark() {
		return carMark;
	}

	public void setCarMark(String carMark) {
		this.carMark = carMark;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
