package kr.co.hk;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/boardList")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String page = request.getParameter("page");
		int intPage = 1;
		if(searchType == null) {
			searchType = "0";
		}
		
		if(searchWord == null) {
			searchWord = "";
		}
		if(page != null) {
			intPage = Integer.parseInt(page);
		}
			
		request.setAttribute("searchType", searchType);
		request.setAttribute("searchWord", searchWord);
		request.setAttribute("pagingCount", BoardDAO.getBoardPagingCount(searchType, searchWord));
		request.setAttribute("list", BoardDAO.getBoardList(searchType, searchWord, intPage));
		request.setAttribute("target", "boardList.jsp");
		RequestDispatcher rd = request.getRequestDispatcher("temp.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
