package com.increpas.bbs;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import spring.sts.util.FileReName;

@Controller
public class EditorController {
	
	
	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private ServletContext application;
	
	@Autowired
	private HttpServletRequest request;
	
	// 에디처에서 이미지가 들어갈 때 해당 이미지를 받아서 
	// 저장할 위치 
	private String editor_img = "/resources/editor_img";
	
	private String bbs_upload = "/resources/bbs_upload";
	
	/*
	 * @RequestMapping("/edit.inc") public ModelAndView editor(BbsVO vo, String
	 * cPage) { ModelAndView mv = new ModelAndView();
	 * 
	 * BbsVO bvo = b_dao.viewContent(vo.getB_idx());
	 * 
	 * mv.addObject("vo", bvo); mv.setViewName("edit"); return mv; }
	 */
	
	@RequestMapping(value="/edit.inc",method = RequestMethod.POST)
	public ModelAndView editors(BbsVO vo, String cPage) {
		ModelAndView mv = new ModelAndView();
		
		String ctx = request.getContentType();
		
		if(ctx.startsWith("multipart")) {
			// 수정한 파일이 있다면 절대 경로화를 시키자!
			String realPath = application.getRealPath(bbs_upload);
			
			// 첨부된 파일을 받자!!
			MultipartFile mf = vo.getFile();
			
			// 저장될 파일명 초기화
			String fname = null;
			
			if(mf.getSize() > 0) {
				// 파일이 들어왔을때
				fname = mf.getOriginalFilename();
				
				// 파일의 이름이 중복 될 수 있으니 파일의 명을 바꿔주는 객체를 호출하자!
				fname = FileReName.FileReName(realPath, fname);
				
				try {
					mf.transferTo(new File(realPath,fname));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				vo.setFile_name(fname);
				vo.setOri_name(mf.getOriginalFilename());
				vo.setIp(request.getRemoteAddr());
				mv.setViewName("redirect:/edit.inc?b_idx"+vo.getB_idx()+"&cPage"+cPage);
				
			}
		}else if(ctx.startsWith("application")) {
			BbsVO bvo = b_dao.viewContent(vo.getB_idx());
			
			mv.addObject("vo", bvo);
			mv.setViewName("edit");
		}
		
		b_dao.edit(vo);
		return mv;
	}
}
