package com.increpas.bbs;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

@Controller
public class ViewController {

	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/view.inc")
	public ModelAndView view(String cPage, String b_idx) {
		ModelAndView mv = new ModelAndView();
		
		BbsVO ar = b_dao.viewContent(b_idx);		
		
		mv.addObject("vo", ar);
		mv.addObject("ip",request.getRemoteAddr());
		mv.setViewName("view");
		return mv;		
	}
	
}
