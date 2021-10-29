package com.increpas.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import spring.sts.util.PagingVO;

@Controller
public class ListController {

	@Autowired
	private BbsDAO b_dao;
	
	int nowPage;
	int numPerPage = 4;
	int pagePerBlock = 3;
	int totalRecord;
	
	
	@RequestMapping("/list.inc")
	public ModelAndView list(String cPage, String bname) {
		ModelAndView mv = new ModelAndView();
		
		if(cPage == null)
			nowPage = 1;
		else
			nowPage = Integer.parseInt(cPage);
		
		if(bname == null)
			bname = "BBS"; // 일반 게시판
		// 총 게시물의 수를 받는다.
		totalRecord = b_dao.total(bname);
		
		// 페이징 기법 객체를 불러와야 한다.(paging)
		PagingVO pvo = new PagingVO(nowPage, numPerPage, pagePerBlock, totalRecord);
		
		BbsVO[]vo = b_dao.getList(pvo.getBegin(), pvo.getEnd(), bname);
		
		//JSP에서 표현해야 하므로 ar을 mv에 저장한다.
		mv.addObject("ar",vo);
		mv.addObject("tag",pvo.getTag());
		mv.addObject("nowPage",nowPage);
		mv.addObject("rowTotal", pvo.getTotalRecord());
		mv.addObject("blockList", pvo.getNumPerPage());
		mv.setViewName("list");
		
		return mv;
	}
	
}
