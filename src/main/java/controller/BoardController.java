package controller;

import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import dao.BoardDao;
import kic.mskim.MskimRequestMapping;
import kic.mskim.RequestMapping;
import model.Board;

@WebServlet("/board/*")
public class BoardController extends MskimRequestMapping {
	BoardDao bd = new BoardDao();

	@RequestMapping("index") // ~~~~/board/index
	public String memberInput(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		return "/WEB-INF/view/board/index.jsp";
	}

	@RequestMapping("boardForm")
	public String boardForm(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		return "/WEB-INF/view/board/boardForm.jsp";
	}

	@RequestMapping("boardPro")
	public String boardPro(HttpServletRequest req, HttpServletResponse res) throws Exception {

		String path = req.getServletContext().getRealPath("/") + "image/board/";
		String filename = null;
		String msg = "게시물 등록 실패";
		String url = "/board/boardForm";

		MultipartRequest multi = new MultipartRequest(req, path, 10 * 1024 * 1024, "UTF-8");

		Board board = new Board();
		board.setBoardid("1");
		board.setName(multi.getParameter("name"));
		board.setPass(multi.getParameter("pass"));
		board.setSubject(multi.getParameter("subject"));
		board.setContent(multi.getParameter("content"));
		board.setFile1(multi.getFilesystemName("file1")); // name="file1"
		System.out.println(board);

		int num = bd.insertBoard(board);
		if (num > 0) {
			msg = "게시물 등록 성공";
			url = "/board/boardList";
		}
		req.setAttribute("msg", msg);
		req.setAttribute("url", url);
		return "/WEB-INF/view/alert.jsp";
	}

	@RequestMapping("boardList")
	public String boardList(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub

		List<Board> li = bd.boardList();
		req.setAttribute("li", li);

		return "/WEB-INF/view/board/boardList.jsp";
	}

	@RequestMapping("boardInfo")
	public String boardInfo(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub

		int num = Integer.parseInt(req.getParameter("num"));

		Board board = bd.oneBoard(num);

		req.setAttribute("board", board);
		return "/WEB-INF/view/board/boardInfo.jsp";
	}

	@RequestMapping("boardUpdateForm")
	public String boardUpdateForm(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub

		int num = Integer.parseInt(req.getParameter("num"));

		Board board = bd.oneBoard(num);

		req.setAttribute("board", board);
		return "/WEB-INF/view/board/boardUpdateForm.jsp";
	}

	@RequestMapping("boardUpdatePro")
	public String boardUpdatePro(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String path = req.getServletContext().getRealPath("/") + "image/board/";
		String filename = null;
		
       
		MultipartRequest multi = new MultipartRequest(req, path, 10 * 1024 * 1024, "UTF-8");
		int num = Integer.parseInt(multi.getParameter("num"));
	
		Board originboard = bd.oneBoard(num);
		
		String msg = "게시물 수정 실패";
		String url = "/board/boardUpdateForm?num="+originboard.getNum();
		if (originboard.getPass().equals(multi.getParameter("pass"))) {

		String nfilename = multi.getFilesystemName("file1");
		Board board = new Board();
		board.setBoardid("1");
		board.setNum(num);
		board.setName(multi.getParameter("name"));
		board.setPass(multi.getParameter("pass"));
		board.setSubject(multi.getParameter("subject"));
		board.setContent(multi.getParameter("content"));
		if (nfilename == null) {
			board.setFile1(multi.getParameter("originfile"));
		} else {
			board.setFile1(nfilename);
		}
		System.out.println(board);
		int count = bd.updateBoard(board);
		if (count > 0) {
			msg = "게시판 수정 성공";
			url = "/board/boardInfo?num="+originboard.getNum();
		}
		} else {
			msg="비밀번호 확인 하세요";
		}
		req.setAttribute("msg", msg);
		req.setAttribute("url", url);
		return "/WEB-INF/view/alert.jsp";

	}
}
