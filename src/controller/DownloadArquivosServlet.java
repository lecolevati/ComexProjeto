package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/download")
public class DownloadArquivosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String DOWNLOAD_DIRECTORY = "C:\\arquivos\\continfo\\1semestre\\materiais\\";

	public DownloadArquivosServlet() {
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
		//A existência do arquivo na pasta não precisa ser testada pois ela é a mesma que gerou a lista de arquivos
		//Arquivos com acentos e caracteres especiais podem trazer problemas ao download, evitar.
		String arq = request.getParameter("arquivo");
		String filePath = DOWNLOAD_DIRECTORY + File.separator + arq;
		File downloadFile = new File(filePath);
		FileInputStream inStream = new FileInputStream(downloadFile);

		ServletContext context = getServletContext();
		//Define o tipo do arquivo para download
		String mimeType = context.getMimeType(filePath);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		//Define o tamanho do arquivo para download
		response.setContentLength((int) downloadFile.length());

		//Define o cabeçalho do html
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		//Download propriamente dito
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		while ((bytesRead = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inStream.close();
		outStream.close();
	}
}
