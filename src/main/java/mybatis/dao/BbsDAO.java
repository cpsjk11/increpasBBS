package mybatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mybatis.vo.BbsVO;
import mybatis.vo.ComVO;

@Component
public class BbsDAO {

	@Autowired
	private SqlSessionTemplate ss;
	
	// 원하는 페이지의 게시물 목록 가져오기
	public BbsVO[] getList(int begin, int end, String bname) {
		BbsVO[]ar = null;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("begin", String.valueOf(begin));
		map.put("end",  String.valueOf(end));
		map.put("bname", bname);
		
		List<BbsVO> list = ss.selectList("bbs.list",map);
		
		if(list != null && !list.isEmpty()) {
			ar = new BbsVO[list.size()];
			list.toArray(ar);
		}
		
		return ar;
	}
	
	// 총 게시물의 수를 반환하는 기능
	public int total(String bname) {
		
		return ss.selectOne("bbs.total",bname);
	}
	
	// 게시물 보기 기능
	public BbsVO viewContent(String b_idx) {
		BbsVO vo = ss.selectOne("bbs.view", b_idx);
		return vo;
	}
	
	// 원글을 저장하는 기능
	public int addBbs(BbsVO vo) {
		int cnt = ss.insert("bbs.add", vo);
		return cnt;
	}
	
	// 댓글 저장 기능
	public int addCom(ComVO vo) {
		int cnt = ss.insert("bbs.addcom", vo);
		return cnt;
	}
	public int edit(BbsVO vo) {
		return ss.update("bbs.edit", vo);
	}
	
	// 게시글 삭제
	public int del(BbsVO vo) {
		return ss.delete("bbs.del", vo);
	}
}
