<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
	<h1>Todo's are:</h1>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Id</th>
				<th>Description</th>
				<th>Target Date</th>
				<th>Is it done?</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${todos}" var="todo">
			<tr>
				<td>${todo.id}</td>
				<td>${todo.desc}</td>
				<td><fmt:formatDate value="${todo.targetDate}" pattern="dd/MM/yyyy"/></td>
				<td>${todo.done}</td>
				<td><a type="button" class="btn btn-success" href="/update-todo?id=${todo.id}">Update</a></td>
				<td><a type="button" class="btn btn-warning" href="/delete-todo?id=${todo.id}">Delete</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	
		<br/>
	<div class="btn btn-primary"><a href="/add-todos" type="button" style="color: white; text-decoration: none;">Add Todo</a></div>
	
</div>
	
<%@ include file="common/footer.jspf"%>