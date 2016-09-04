<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UPLOAD</title>
</head>
<body>
	<div align="center">
		<h3>
			Serviço de armazenamento de Projetos. EM DESENVOLVIMENTO <br />
			Carregamento de arquivos disponível com as seguintes limitações:<br />
			* Arquivos de até 2MB<br />
			* 3 Arquivos por aluno em cada disciplina<br /> 
		</h3>
		<br />
		<br />
		<h2>PROJETOS EM COMEX</h2>
		<form action="upload" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td>Disciplina:</td>
					<td>
						<select name="disciplina">
							<option value="0">--Selecione a Disciplina--</option>
							<option value="proj1">Projetos em COMEX I</option>
							<option value="proj2">Projetos em COMEX II</option>
							<option value="proj3">Projetos em COMEX III</option>
							<option value="proj4">Projetos em COMEX IV</option>
							<option value="proj5">Projetos em COMEX V</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>RA:</td>
					<td><input type="text" name="ra" size="20" required /></td>
				</tr>
			</table>
			<input type="file" id="file" name="file1" required />
			<input type="submit" /> <br> <br>
			<c:if test="${not empty erro }">
				<c:forEach items="${erro }" var="e">
					<c:out value="${e }" />
					<br />
				</c:forEach>
			</c:if>
			<c:if test="${not empty message }">
				<c:out value="Arquivos Carregados : " />
				<br />
				<c:forEach items="${message }" var="msg">
					<c:out value="${msg }" />
					<br />
				</c:forEach>
			</c:if>
		</form>
	</div>
</body>
</html>