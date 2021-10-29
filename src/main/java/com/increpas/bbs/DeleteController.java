package com.increpas.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

@Controller
public class DeleteController {

	@Autowired
	private BbsDAO b_dao;
	
	@RequestMapping("/delete.mo")
	public ModelAndView del(BbsVO vo, String cPage) {
		ModelAndView mv = new ModelAndView();
		b_dao.del(vo);
		mv.setViewName("redirect:/list.inc?cPage="+cPage);
		return mv;
	}
	
}
