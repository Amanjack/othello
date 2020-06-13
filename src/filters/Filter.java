package filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import models.Logic;
import models.Reversi;

/**
 * Servlet Filter implementation class Filter
 */
@WebFilter("/index.jsp")
public class Filter implements javax.servlet.Filter {

    /**
     * Default constructor.
     */
    public Filter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

    	HttpSession session = ((HttpServletRequest)request).getSession();

    	if((Reversi) session.getAttribute("reversi") == null){

    		// セッションが無ければ、初期設定を行う
    		Reversi reversi = new Reversi();
			Logic.initialize(reversi);
	    	session.setAttribute("reversi", reversi);
    	}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
