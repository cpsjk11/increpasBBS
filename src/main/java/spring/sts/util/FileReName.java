package spring.sts.util;

import java.io.File;

public class FileReName {
	// 만약 같은 명의 파일이 넘어왔다면 이름을 바꿔주는 함수를 정의하자
	public static String FileReName(String realPath, String fname) {
		// 먼저 서브스트링을 사용해서 확장자 앞까지 파일의 이름을 자른다!
		int i = fname.lastIndexOf(".");
		// 확장자 직전까지의 길이를 알아냈으니 확장자를 잘라보자!
		String f_name = fname.substring(0, i);
		String filename = fname.substring(i);
		
		String totalPath = realPath+"/"+fname;
		
		// 이제 파일 객체를 생성을해서 현재 이 파일이 실제로 존재하는지의 여부를 따진다.
		File f = new File(totalPath);
		
		// 파일명이 같다면 뒤에 숫자를 1씩 증가를 시켜주자.
		int idx = 1;
		
		while(f != null && f.exists()) {
			// 파일명이 동일할때 수행하는 공간이다.
			StringBuffer sb = new StringBuffer();
			sb.append(f_name);
			sb.append("(");
			sb.append(idx++);
			sb.append(")");
			sb.append(filename);
			
			fname = sb.toString();
			
			totalPath = realPath+"/"+fname;
			
			f = new File(totalPath);
		}
		return fname;
	}

}
