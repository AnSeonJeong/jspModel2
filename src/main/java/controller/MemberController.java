package controller;

import java.io.IOException;
import java.security.PublicKey;

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
import net.sf.json.JSONObject;

@WebServlet("/member")
public class MemberController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}
	
	private void doProc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DBConnection.initConnection();
		req.setCharacterEncoding("utf-8");
		
		String param = req.getParameter("param");
		
		if(param.equals("login")) {
			resp.sendRedirect("login.jsp");
		} 
		else if(param.equals("regi")) {			
			resp.sendRedirect("regi.jsp");
		}
		else if(param.equals("idcheck")) {	// ajax		
			String id = req.getParameter("id"); // regi에서 온 id
			
			// DB로 접근
			MemberDao dao = MemberDao.getInstance();
			boolean b = dao.getId(id);
			
			String str = "NO";
			if(b == false) str = "YES";
			
			JSONObject jObj = new JSONObject();
			jObj.put("str", str);
			
			resp.setContentType("application/x-json;charset=utf-8");
			resp.getWriter().print(jObj);
		}
		else if(param.equals("regiAf")) {	// 회원가입
			
			// parameter 값
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			String name = req.getParameter("name");
			String email = req.getParameter("email");
			
			System.out.println(id+" "+pwd+" "+name+" "+email);
			
			// db에 저장
			MemberDao dao = MemberDao.getInstance();
			MemberDto mem = new MemberDto(id, pwd, name, email, 0);
			boolean isS = dao.addMember(mem);
			
			String message = "";
			if(isS) {
				message = "MEMBER_YES";
			} else {
				message = "MEMBER_NO";
			}
			
			// login.jsp로 이동
			req.setAttribute("message", message);
			// req.getRequestDispatcher("message").forward(req, resp);
			forward("message.jsp", req, resp);
			
		}
		
		else if(param.equals("loginAf")) {
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			
			MemberDao dao = MemberDao.getInstance();
			MemberDto login = dao.login(id, pwd);
			
			if(login != null) {
				// session에 저장
				req.getSession().setAttribute("login", login);		
				resp.sendRedirect("bbs?param=bbslist");
				
				
			}
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
