package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Logic;
import models.Reversi;

/**
 * Servlet implementation class othelloServlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	request.setCharacterEncoding("UTF-8");

    	HttpSession session = request.getSession();
    	Reversi r = (Reversi) session.getAttribute("reversi");

		if(r != null){

		    // ゲームが終了した場合
			if(r.isFin()){
				Logic.initialize(r);
		    	session.setAttribute("reversi", r);
			} else {

		    	// 盤面からリクエストを受けた場合
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						String param = i+"_"+j;
						if (!(request.getParameter(param) == null || request.getParameter(param).isEmpty())) {
							Logic.setStone(r, j, i);
					    	session.setAttribute("reversi", r);
						}
					}
				}

				// "待った"ボタンが押された場合
		    	if (!(request.getParameter("stop") == null || request.getParameter("stop").isEmpty())) {

					Logic.stop(r);
			    	session.setAttribute("reversi", r);

				// "パス"ボタンが押された場合
				} else if (!(request.getParameter("pass") == null || request.getParameter("pass").isEmpty())) {

					Logic.pass(r);
			    	session.setAttribute("reversi", r);

				// "最初から"ボタンが押された場合
				} else if (!(request.getParameter("reset") == null || request.getParameter("reset").isEmpty())) {

					Logic.initialize(r);
			    	session.setAttribute("reversi", r);
				}
			}
		} else {

			// インスタンスがnullの場合
    		Reversi reversi = new Reversi();
			Logic.initialize(reversi);
	    	session.setAttribute("reversi", reversi);
		}

        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }
}
