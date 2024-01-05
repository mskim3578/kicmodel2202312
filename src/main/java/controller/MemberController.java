package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import dao.MemberDao;
import dao.MemberMybatisDao;
import kic.mskim.Login;
import kic.mskim.MskimRequestMapping;
import kic.mskim.RequestMapping;
import model.KicMember;


@WebServlet("/member/*")
public class MemberController  extends  MskimRequestMapping {
	
	
	MemberMybatisDao md = new  MemberMybatisDao();
	HttpSession  session ;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		this.session =  request.getSession();
		super.service(request, resp);
	}

	
	@RequestMapping("memberInput")
	public String memberInput(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		return "/WEB-INF/view/member/memberInput.jsp";
	}
	@RequestMapping("index")
	public String index(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		return "/WEB-INF/view/member/index.jsp";
	}
	@RequestMapping("memberPro")
	public String memberPro(HttpServletRequest request, HttpServletResponse response) throws Exception {
		KicMember kicmem = new KicMember();
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String name = request.getParameter("name");
		int  gender =  Integer.parseInt(request.getParameter("gender"));
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		String picture = request.getParameter("picture");  //
		kicmem.setId(id);
		kicmem.setPass(pass);
		kicmem.setName(name);
		kicmem.setGender(gender);
		kicmem.setTel(tel);
		kicmem.setEmail(email);
		kicmem.setPicture(picture);//
	
		System.out.println(kicmem);//
		int num = md.insertMember(kicmem);
		String msg = "저장 하였습니다";
		String url = "/member/loginForm";
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		return "/WEB-INF/view/alert.jsp";
	}

	
	@Login(key = "id")   //login확인 "id"
	@RequestMapping("memberInfo")
	public String memberInfo(HttpServletRequest request, HttpServletResponse res) throws Exception {
	
	
		 String login = (String) session.getAttribute("id");
		KicMember mem = md.oneMember(login);
		request.setAttribute("mem", mem);
		return "/WEB-INF/view/member/memberInfo.jsp";
	}
	
	
	
	@RequestMapping("loginForm")
	public String loginForm(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		return "/WEB-INF/view/member/loginForm.jsp";
	}
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse res) throws Exception {
		
		  session.invalidate();
		  request.setAttribute("msg", "logout 했습니다");
			request.setAttribute("url", "/member/loginForm");
			
			return "/WEB-INF/view/alert.jsp";
		  
		
	}
	
	@RequestMapping("loginPro")
	public String loginPro(HttpServletRequest request, HttpServletResponse res) throws Exception {
		String id = request.getParameter("id");
		String pass= request.getParameter("pass");
	
		KicMember mem = md.oneMember(id);
   
		String msg = "아이디를 확인하세요";
		String url = "/member/loginForm";
		if (mem !=null) {  //id 존재함
			if (pass.equals(mem.getPass())) {   //login ok
				session.setAttribute("id", id);
				msg = mem.getName() + "님이 로그인 하셨습니다";
				url = "/member/index";
			} else {		
				msg = "비밀번호를 확인하세요";
			}	
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		return "/WEB-INF/view/alert.jsp";
	}
	
	
	@Login(key = "id")   //login확인 "id"
	@RequestMapping("memberUpdateForm")
	public String memberUpdateForm(HttpServletRequest request, HttpServletResponse res) throws Exception {
	
		String login = (String) session.getAttribute("id");
	
		KicMember mem = md.oneMember(login);
		  
		  
		request.setAttribute("mem", mem);
		  
			
			return "/WEB-INF/view/member/memberUpdateForm.jsp";
		  
		
	}
	
	@RequestMapping("memberUpdatePro")
	public String memberUpdatePro(HttpServletRequest request, HttpServletResponse res) throws Exception {
		
		String login = (String) session.getAttribute("id");
		
		KicMember mem = new KicMember();  //client에서 넘어온 자료
		mem.setId(login);   //session 저장 logout이면 에러남
		mem.setPass(request.getParameter("pass"));
		mem.setName(request.getParameter("name"));
		mem.setGender(Integer.parseInt(request.getParameter("gender")));
		mem.setTel(request.getParameter("tel"));
		mem.setEmail(request.getParameter("email"));
		mem.setPicture(request.getParameter("picture"));
		System.out.println(mem+"==============");
		
		KicMember memdb = md.oneMember(login);  //db에서 넘어온 자료
		String msg = "수정 되지 않았습니다";
		String url="/member/memberUpdateForm";
		if (memdb!=null) {
			if (memdb.getPass().equals(mem.getPass())) {  //수정 ok
				           		msg = "수정 되었습니다";
								md.updateMember(mem); 
								url="/member/memberInfo";
			} else {
				msg = "비밀번호가 틀렸습니다";			}			
		}
			
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		return "/WEB-INF/view/alert.jsp";
		  	
	}
	
	@RequestMapping("memberDeleteForm")
	public String memberDeleteForm(HttpServletRequest request, HttpServletResponse res) throws Exception {
	
		  
			
			return "/WEB-INF/view/member/memberDeleteForm.jsp";
		  
		
	}
	
	@RequestMapping("memberDeletePro")
	public String memberDeletePro(HttpServletRequest request, HttpServletResponse res) throws Exception {
	
		String login = (String) session.getAttribute("id");
		String pass = request.getParameter("pass");
		
		KicMember memdb = md.oneMember(login);
		String msg = "탈퇴되지 않았습니다";
		String url = "/member/memberDeleteForm";

		if (memdb!=null) {
			if(memdb.getPass().equals(pass))   {   //비밀번호 확인
				msg="탈퇴 됬습니다  ";
			    md.deleteMember(login);
			    session.invalidate();
			    url = "/member/index";
			
			
			} else {
				 msg="비밀번호가 틀렸습니다 ";
			}
			
			
		}
		  
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		return "/WEB-INF/view/alert.jsp";
		  
		
	}
	
	
	@RequestMapping("memberPassForm")
	public String memberPassForm(HttpServletRequest request, HttpServletResponse res) throws Exception {
				return "/WEB-INF/view/member/memberPassForm.jsp";
	}
	
	
	@RequestMapping("memberPassPro")
	public String memberPassPro(HttpServletRequest request, HttpServletResponse res) throws Exception {
		String pass = request.getParameter("pass");
		String chgpass= request.getParameter("chgpass");
	
	
		String login = (String) session.getAttribute("id");
	
		//1. md.oneMember(login) 
		//2. db 저장 pass 확인 : msg, url 변경
		//3  md.passMember()
	
		KicMember memdb = md.oneMember(login);
		String msg="비밀번호 수정을  실패 했습니다";
		String url="/member/memberPassForm";
		  if (memdb!=null) {
			  if (memdb.getPass().equals(pass)) { 
				        md.passMember(login, chgpass);
						msg=login+"님이 비밀번호가 수정 되었습니다";
						url="/member/index";
			  } else {
				    msg="비밀번호가 틀렸습니다 ";
			  }	  
		  } 	
		
		  request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			
			return "/WEB-INF/view/alert.jsp";
	}
	
	@RequestMapping("pictureimgForm")
	public String pictureimgForm(HttpServletRequest request, HttpServletResponse res) throws Exception {
				return "/single/pictureimgForm.jsp";
	}
	@RequestMapping("picturePro")
	public String picturePro(HttpServletRequest request, HttpServletResponse res) throws Exception {
		        String path = 	request.getServletContext().getRealPath("/")+"/image/member/picture/";
		        System.out.println(path);
				String filename = null;		
				try {
					MultipartRequest  multi = 
					new MultipartRequest(request,path,10*1024*1024,"UTF-8");

					filename = multi.getFilesystemName("picture");

					} catch (IOException e) {
						e.printStackTrace();
					}

					request.setAttribute("filename", filename);
				
		
		
		
		return "/single/picturePro.jsp";
	}
}
