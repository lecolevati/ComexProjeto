package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String erro = "";
		String url = "index.jsp";
		try {
			if (login.trim().equals("") || senha.trim().equals("")){
				erro = "Digite login e senha";
			} else {
				if (login.equals("professor") && senha.equals("74k5kqa1")){
					url = "arquivo.jsp";
				} else {
					erro = "Login ou Senha inválidos";
				}
			}
		} catch (Exception e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("erro", erro);
			RequestDispatcher view = request
					.getRequestDispatcher(url);
			view.forward(request, response);
		}
	}
}
