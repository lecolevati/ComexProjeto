package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Disciplina;
import persistence.ArquivosDao;
import persistence.DisciplinaDao;
import persistence.IArquivosDao;
import persistence.IDisciplinaDao;

@WebServlet("/materiais")
public class ArquivosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ArquivosServlet() {
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
		String erro = "";
		String disciplina = request.getParameter("disciplina");
		
		List<String> listaArquivos = new ArrayList<String>();
		d.setCodigo(disciplina);
		try {
			File dir = new File(diretorio+File.separator+d.getPasta());
			if (dir.exists()) {
				File[] lista = dir.listFiles();
				for (File f : lista) {
					if (f.isFile()) {
						listaArquivos.add(f.getName());
					}
				}
			} else {
				erro = "Falha ao buscar arquivos";
			}
		} catch (SQLException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("listaArquivos", listaArquivos);
			request.setAttribute("erro", erro);
			request.setAttribute("disciplina", d);
			RequestDispatcher view = request
					.getRequestDispatcher("materiais.jsp");
			view.forward(request, response);
		}

	}

}
