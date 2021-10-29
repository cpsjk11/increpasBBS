package com.increpas.bbs;

import java.io.File;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import mybatis.vo.ComVO;
import spring.sts.util.FileReName;
import spring.vo.ImgVO;

@Controller
public class WriteController {
	
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
	
	@RequestMapping("/write.inc")
	public String write() {
		return "write";
	}
	
	@RequestMapping(value="/saveImg.inc",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> sevaImg(ImgVO vo){
		Map<String, String> map = new HashMap<String, String>();
		
		// 파일 업로드가 되었을때 수행하는 곳
		// 절대경로 부터 잡자!
		String realPath = application.getRealPath(editor_img);
		
		// 업로드 된 파일을 가져와서 사이지를 확인하자!
		MultipartFile mf = vo.getS_file();
		
		// 먼저 파일의 이름이 중복 될 수 있으니 null로 초기화 하자
		String fname = null;
		
		if(mf.getSize() > 0) {
			// 파일이 들어온 경우
			fname = mf.getOriginalFilename();
			// 만약 파일의 이름이 같을 수 있으니 파일의 이름을 바꿔주는 클래스를 호출하자!
			fname = FileReName.FileReName(realPath, fname);
			
			// 파일의 이름이 변경된 값을 지정한 곳에 업로드 시키자!!!
			try {
				mf.transferTo(new File(realPath, fname));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String cpath = request.getContextPath();
		// 이제 저장이 되었으니 값들을 저장해서 보내주자!
		map.put("urls", cpath+editor_img);
		map.put("name", fname);
		
		return map;
	}
	
	// 게시물을 저장하는 기능
	@RequestMapping(value = "/write.inc",method = RequestMethod.POST)
	public ModelAndView add(BbsVO vo) {
		ModelAndView mv = new ModelAndView();
		// 여기도 마찬가지로 파일을 업로드를 해줘야 한다.
		
		// 파일명 준비
		String fname = null;
		
		MultipartFile mf = vo.getFile();
		
		// 폴더 절대경로화
		String realPath = application.getRealPath(bbs_upload);
		
		if(mf.getSize() > 0) {
			// 파일이 들어왔을 경우다.
			
			fname = mf.getOriginalFilename();
			
			// 파일의 이름이 중복될 수 있으니 확인하자
			fname = FileReName.FileReName(realPath, fname);
				
			try {
				// 파일의 이름도 변경 했으니 이제 저장을 하자!
				mf.transferTo(new File(realPath,fname));
			} catch (Exception e) {
				// TODO: handle exception
			}
			// 저장을 완료 했으니 변경된 이름과 파일의 원본이름을 vo객체에다가 저장하자
			vo.setFile_name(fname);
			vo.setOri_name(mf.getOriginalFilename());
		}
		// 이쪽 영역은 파일이 첨부되지 않았을 때 그리고 첨부됬을 때 수헹하는 공간이니까
		// 여기서 사용자의 ip랑 bname을 받자!!
		String ip = request.getRemoteAddr();
		String bname = vo.getBname();
		if(vo.getBname() != null)
			vo.setBname(vo.getBname());
		else
			vo.setBname("BBS");
		
		vo.setIp(ip);
		
		// 모든 준비가 끝났으니 이제 게시글을 정말 저장하자!!
		int cnt = b_dao.addBbs(vo);
		
		mv.setViewName("redirect:/list.inc");
		
		return mv;
	}
	
	@RequestMapping(value="/ans_write.mo")
	@ResponseBody
	public Map<String, String>ans_write(ComVO vo){
		Map<String, String> map = new HashMap<String, String>();
		
		int cnt = b_dao.addCom(vo);
		
		Date date = new Date(System.currentTimeMillis());
		
		map.put("writer", vo.getWriter());
		map.put("date", date.toString());
		map.put("content", vo.getContent());
		
		return map;
	}
	
}













