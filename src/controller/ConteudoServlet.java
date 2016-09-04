package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/conteudo")
public class ConteudoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Semelhante ao do UPLOAD
	private static String DOWNLOAD_DIRECTORY = "C:\\temp";

	public ConteudoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<String> erro = new ArrayList<String>();
		List<String> lista = new ArrayList<String>();
		File dir = new File(DOWNLOAD_DIRECTORY);
		try {
			if (!dir.exists()) {
				erro.add("Diretório Inexistente");
			} else {
				File[] vetArquivos = dir.listFiles();
				for (File f : vetArquivos) {
					lista.add(f.getName());
				}
			}
		} catch (Exception e) {
			erro.add(e.getMessage());
		} finally {
			String url = "download.jsp";
			request.setAttribute("lista", lista);
			request.setAttribute("erro", erro);
			RequestDispatcher view = request
					.getRequestDispatcher(url);
			view.forward(request, response);
		}
	}
}
