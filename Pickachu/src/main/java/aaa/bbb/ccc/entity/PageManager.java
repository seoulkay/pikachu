package aaa.bbb.ccc.entity;

public class PageManager {
	private Integer startPage;
	private Integer endPage;
	
	private Integer maxPager;
	
	private Integer pageSize;
	private Integer currentPage;
	private Integer totalSize;
	
	private String search;
	
	
	
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public Integer getStartPage() {
		return startPage;
	}
	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}
	public Integer getEndPage() {
		return endPage;
	}
	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}
	public Integer getMaxPager() {
		return maxPager;
	}
	public void setMaxPager(Integer maxPager) {
		this.maxPager = maxPager;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}
	
	
}
