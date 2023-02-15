package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BbsDao;
import dao.MemberDao;
import db.DBConnection;
import dto.BbsDto;
import dto.MemberDto;

@WebServlet("/bbs")
public class BbsController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}
	
	public void doProc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DBConnection.initConnection();
		req.setCharacterEncoding("utf-8");
		
		String param = req.getParameter("param");
		System.out.println("param=" + param);
		if(param.equals("bbslist")) {
			// 추가
			String choice = req.getParameter("choice");
			String search = req.getParameter("search");
			
			if(choice == null || search == null) {
				choice = "";
				search = "";
			}
			
			req.setAttribute("choice", choice);
			req.setAttribute("search", search);
			
			System.out.println("choice=" + choice + "search=" + search);
			BbsDao dao = BbsDao.getInstance();
			// List<BbsDto> bbslist = dao.getBbsSearchList(choice, search);	

			// page number
			String sPageNumber = req.getParameter("pageNumber");
			int pageNumber = 0;
			if(sPageNumber != null && !sPageNumber.equals("")) {
				pageNumber = Integer.parseInt(sPageNumber);
			}
			
			List<BbsDto> list = dao.getBbsPageList(choice, search, pageNumber);
			
			//글의 총수
			int count = dao.getAllBbs(choice, search);
			
			// 페이지의 총수
			int pageBbs = count/10;
			if((count%10) > 0) pageBbs += 1;
			
			req.setAttribute("pageBbs", pageBbs);
			req.setAttribute("pageNumber", pageNumber);
			req.setAttribute("bbslist", list);
			forward("bbslist.jsp", req, resp);
		}
		
		else if(param.equals("bbswrite")) {
			resp.sendRedirect("bbsWrite.jsp");
		}
		
		else if(param.equals("bbswriteAf")) {
			
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			boolean isS = BbsDao.getInstance().write(new BbsDto(id,title,content));
			
			String bbswrite = "";
			
			if(isS) {
				bbswrite = "BBS_ADD_OK";
			} 
			else {
				bbswrite = "BBS_ADD_NO";
			}
			req.setAttribute("bbswrite", bbswrite);
			forward("message.jsp", req, resp);
		}
		
		else if(param.equals("bbsdetail")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			
			BbsDao dao = BbsDao.getInstance();
			BbsDto bbsdetail = dao.getBbs(seq);
			dao.readcount(seq);
			
			req.setAttribute("bbsdetail", bbsdetail);
			forward("bbsdetail.jsp", req, resp);
		}
		
		else if(param.equals("answer")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			
			BbsDto dto = BbsDao.getInstance().getBbs(seq);
			
			req.setAttribute("seq", seq);
			req.setAttribute("dto", dto);
			forward("answer.jsp", req, resp);
		}
		
		else if(param.equals("answerAf")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			boolean isS = BbsDao.getInstance().answer(seq, new BbsDto(id, title, content));
			String bbsanswer = "";
			if(isS) {
				bbsanswer = "ANSWER_OK";
			} else {				
				bbsanswer = "ANSWER_NO";
			}
			
			req.setAttribute("bbsanswer", bbsanswer);
			forward("message.jsp", req, resp);
		}
		
		else if(param.equals("updateBbs")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			
			BbsDto dto = BbsDao.getInstance().getBbs(seq);
			
			req.setAttribute("seq", seq);
			req.setAttribute("dto", dto);
			forward("updateBbs.jsp", req, resp);
		}
		
		else if(param.equals("updateBbsAf")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			boolean isS = BbsDao.getInstance().updateBbs(seq, title, content);
			String bbsUpdate = "UPDATE_OK";
			if(!isS) {
				bbsUpdate = "UPDATE_NO";
			}
			
			req.setAttribute("seq", seq);
			req.setAttribute("bbsUpdate", bbsUpdate);
			forward("message.jsp", req, resp);
		}
		
		else if(param.equals("deleteBbs")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			
			boolean isS = BbsDao.getInstance().deleteBbs(seq);
			String bbsDelete = "DELETE_OK";
			if(!isS) {				
				bbsDelete = "DELETE_NO";
			}
			
			req.setAttribute("seq", seq);
			req.setAttribute("bbsDelete", bbsDelete);
			forward("message.jsp", req, resp);
		}
	}
	
	public void forward(String linkName, HttpServletRequest req, HttpServletResponse resp) {
		RequestDispatcher dispatcher = req.getRequestDispatcher(linkName);
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
