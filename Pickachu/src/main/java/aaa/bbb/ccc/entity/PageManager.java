package aaa.bbb.ccc.entity;

public class PageManager {

	private Integer pageSize;

	private Integer currentPage;
	
	private Integer totalSize;
	
	private int startPage;
	
	private int endPage;
	
	private int total;
	
	private int maxPager;

	private String search;
	
	

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
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

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getMaxPager() {
		return maxPager;
	}

	public void setMaxPager(int maxPager) {
		this.maxPager = maxPager;
	}
	
	
	
}

