package site.railcartracker.site_metrics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PaginationResponse<T> {
	private List<T> data;
	private int page;

	@JsonProperty("pageSize")
	private int pageSize;

	@JsonProperty("totalPages")
	private int totalPages;

	@JsonProperty("totalElements")
	private long totalElements;

	@JsonProperty("sortColumn")
	private String sortColumn;

	@JsonProperty("sortDirection")
	private String sortDirection;

	@JsonProperty("showingFrom")
	private int showingFrom;

	@JsonProperty("showingTo")
	private int showingTo;

	// Constructors
	public PaginationResponse() {
	}

	public PaginationResponse(List<T> data, int page, int pageSize, int totalPages, long totalElements,
			String sortColumn, String sortDirection) {
		this.data = data;
		this.page = page;
		this.pageSize = pageSize;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
		this.sortColumn = sortColumn;
		this.sortDirection = sortDirection;
		this.showingFrom = calculateShowingFrom();
		this.showingTo = calculateShowingTo();
	}

	// Getters and setters
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
		this.showingFrom = calculateShowingFrom();
		this.showingTo = calculateShowingTo();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.showingFrom = calculateShowingFrom();
		this.showingTo = calculateShowingTo();
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
		this.showingTo = calculateShowingTo();
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public int getShowingFrom() {
		return showingFrom;
	}

	public int getShowingTo() {
		return showingTo;
	}

	private int calculateShowingFrom() {
		return totalElements == 0 ? 0 : (page * pageSize) + 1;
	}

	private int calculateShowingTo() {
		if (totalElements == 0)
			return 0;
		int calculatedTo = (page + 1) * pageSize;
		return Math.min(calculatedTo, (int) totalElements);
	}

	// Static factory method for easy creation from Page<T>
	public static <T> PaginationResponse<T> from(org.springframework.data.domain.Page<T> page, String sortColumn,
			String sortDirection) {
		return new PaginationResponse<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalPages(),
				page.getTotalElements(), sortColumn != null ? sortColumn : "",
				sortDirection != null ? sortDirection : "");
	}
}
