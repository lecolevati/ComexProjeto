package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

@WebServlet("/upload")
public class UploadArquivosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Definir a pasta de upload aqui (não recomendo usar a pasta do próprio
	// projeto)
	private static String UPLOAD_DIRECTORY = "C:\\COMEX\\Projetos";

	public UploadArquivosServlet() {
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

	@SuppressWarnings("unchecked")
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<String> resp = new ArrayList<String>();
		List<String> erro = new ArrayList<String>();
		List<FileItem> items; // Aqui podem vir arquivos e input texts, selects,
								// etc.
		String ra = "";
		String disciplina = "";
		int qtdArquivos = 0;
		try {
			items = new ServletFileUpload(new DiskFileItemFactory())
					.parseRequest(request);
			for (FileItem item : items) { // Para cada componente do html que
											// veio por request
				if (item.isFormField()) {
					if (item.getFieldName().equals("ra")) {
						ra = item.getString();
						if (ra.trim().equals("")){
							erro.add("Entre com seu RA");
							break;
						}
					}
					if (item.getFieldName().equals("disciplina")) {
						disciplina = item.getString();
						if (disciplina.equals("0")) {
							erro.add("Selecione uma disciplina");
							break;
						}
					}
				} else { // Aqui pegamos os arquivos
					if (!disciplina.equals("0") && !disciplina.equals("")) {
						InputStream inputStream = null;
						OutputStream outputStream = null;
						try {
							String filename = FilenameUtils.getName(item
									.getName());
							inputStream = item.getInputStream();
							long tamanho = item.getSize();
							File dir = new File(UPLOAD_DIRECTORY);
							if (!dir.exists()) {// Se o diretorio não existir,
												// cria ele
								dir.mkdir();
							}
							dir = new File(UPLOAD_DIRECTORY + File.separator
									+ disciplina);
							if (!dir.exists()) {// Se o diretorio não existir,
												// cria ele
								dir.mkdir();
							}
							dir = new File(UPLOAD_DIRECTORY + File.separator
									+ disciplina + File.separator + ra);
							if (!dir.exists()) {// Se o diretorio não existir,
												// cria ele
								dir.mkdir();
							}
							File[] listaArquivos = dir.listFiles();
							boolean sub = false;
							for (File f : listaArquivos){
								if (f.getName().equals(filename)){
									sub = true;
									break;
								}
							}
							qtdArquivos = listaArquivos.length;
							if (((listaArquivos != null && qtdArquivos < 3) && tamanho <= 2097152) || (sub && tamanho <= 2097152)){

								File arquivo = new File(dir.getAbsolutePath(),
										filename);

								if (arquivo.exists()) { // Esse if substitui o
														// arquivo, caso ele já
														// exista na pasta
									arquivo.delete();
									
								}
								// Escrita do arquivo, o upload de fato;
								outputStream = new FileOutputStream(arquivo);

								int read = 0;
								byte[] bytes = new byte[1024];

								while ((read = inputStream.read(bytes)) != -1) {
									outputStream.write(bytes, 0, read);
								}
								resp.add(filename);
								if (sub){
									resp.add("Arquivo substituído");
								}
							} else {
								if (tamanho > 2097152){
									erro.add("Tamanho do arquivo excede o limite de 2MB");
								}
								if (qtdArquivos >= 3){
									erro.add("Limite de arquivos excedido");
								}
							}
						} catch (Exception e) {
							erro.add(e.getMessage());
						} finally {
							if (inputStream != null) {
								try {
									inputStream.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							if (outputStream != null) {
								try {
									outputStream.flush();
									outputStream.close();
								} catch (IOException e) {
									e.printStackTrace();
								}

							}
						}
					}
				}
			}
		} catch (FileUploadException | NoSuchMethodError e) {
			e.printStackTrace();
			erro.add(e.getMessage());
		} finally {
			String url = "/envio.jsp"; // url de retorno do servlet
			request.setAttribute("erro", erro);
			request.setAttribute("message", resp);
			getServletContext().getRequestDispatcher(url).forward(request,
					response);
		}

	}
}
