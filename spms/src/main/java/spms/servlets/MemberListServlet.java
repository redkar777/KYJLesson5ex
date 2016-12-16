package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

// ServletContext에 보관된 Connection 객체 사용  
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			request.setCharacterEncoding("utf-8");

			ServletContext sc = this.getServletContext();
			Connection servletConn = (Connection) sc.getAttribute("conn"); 
			
			MemberDao memberDao = new MemberDao();
			//memberDao.connection ==null;
			memberDao.setConnection(servletConn);
			//memberDao.connection == serlvetConnection
			response.setContentType("text/html; charset=UTF-8");
			
			List<Member> members = memberDao.selectList();
			request.setAttribute("members", members);
			
			
			// request에 회원 목록 데이터 보관한다.
			
			
			// JSP로 출력을 위임한다.
			RequestDispatcher rd = request.getRequestDispatcher(
					"/member/MemberList.jsp");
			rd.include(request, response);
			
			// 데이터베이스에서 회원 정보를 가져와 Member에 담는다.
			// 그리고 Member객체를 ArrayList에 추가한다.
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		}
			
		}

	}
