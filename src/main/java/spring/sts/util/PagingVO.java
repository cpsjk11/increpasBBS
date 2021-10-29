package spring.sts.util;

public class PagingVO {
	// 여기는 페이징 기법에 필요한 변수명들을 정의 할 공간이다.
	private int nowPage, // 현제페이지 값
				numPerPage, // 한 페이지당 보여질 게시물의 수
				totalRecord, // 총 게시물의 수
				pagePerBlock, // 한 블러당 보여질 페이지의 수
				totalPage, // 총 페이지의 수
				begin, // 한 페이지당 보여질 시작수
				end, // 한 페이지당 보여질 마지막수
				startPage, //보여질 페이지 블럭에서의 시작 페이지
				endPage, // 보여질 페이지 블럭에서의 마지막 페이지
				bname; // 게시판 종류 선택
	private String tag;
	private boolean nextPage = false , previousPage = false;
	
	public PagingVO(int nowPage, int numPerPage, int pagePerBlock, int totalRecord) {
		this.nowPage = nowPage;
		this.numPerPage = numPerPage;
		this.pagePerBlock = pagePerBlock;
		this.totalRecord = totalRecord;
		
		// 일단 총 게시물의 수를 받았으니 총 페이지의 값을 구하자
		totalPage = (int)Math.ceil((double)totalRecord/numPerPage);
		
		// 현재 페이지 값보다 총 페이지의 값이 크면 안된다
		if(nowPage > totalPage)
			nowPage = totalPage;
		
		// 현재 블럭의 시작페이지 값과 마지막페이지 값을 구하자!
		startPage = (int)((nowPage - 1)/pagePerBlock)*pagePerBlock+1;
		endPage = startPage+pagePerBlock -1; 
		
		// 마지막 페이지의 값이 전체 페이지의 값보다 크다면 마지막 페이지 값을 
        // 전체 페이지 값으로 지정
		if(endPage > totalPage)
			endPage = totalPage;
		
		// 현재 페이지 값에 의해 시작 게시물의 행번호와 마지막 게시물의 행번호를 지정하여 
        // 현재 페이지의 보여질 게시물 목록을 얻을 준비를 하자!
		begin = ((nowPage -1)*numPerPage)+1;
		end = nowPage*numPerPage;
		
		StringBuffer sb = new StringBuffer();
		
		// 이전 기능과 다음 기능 줄때 구분하기!
		if(endPage < totalPage)
			nextPage = true;
		
		if(1 < startPage)
			previousPage = true;
		

		
		if(previousPage) {
			sb.append("<li><a href='list.inc?cPage=");
			sb.append(startPage - pagePerBlock);
			sb.append("'>&lt;</a></li>");
		}else{
			sb.append("<li class='disable'>&lt;</li>");
		}
		
		// 표현할 게시판 아래의 숫자를 만들자!
		for(int i = startPage; i<= endPage; i++) {
			
			if(nowPage == i) {
				sb.append("<li class='now'>");
				sb.append(i);
				sb.append("</li>");
			}else {
				sb.append("<li><a href='list.inc?cPage=");
				sb.append(i);
				sb.append("'>");
				sb.append(i);
				sb.append("</a></li>");
			}
		}
		
		if(nextPage) {
			sb.append("<li><a href='list.inc?cPage=");
			sb.append(startPage + pagePerBlock);
			sb.append("'>&gt;</a></li>");
		}else
			sb.append("<li class='disable'>&gt;</li>");
		
		tag = sb.toString();
	}
	
	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getPagePerBlock() {
		return pagePerBlock;
	}

	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
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

	public int getBname() {
		return bname;
	}

	public void setBname(int bname) {
		this.bname = bname;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean isNextPage() {
		return nextPage;
	}

	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}

	public boolean isPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(boolean previousPage) {
		this.previousPage = previousPage;
	}
	
	
	
				
}
