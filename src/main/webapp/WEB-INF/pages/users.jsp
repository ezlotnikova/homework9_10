<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<title>Users</title>
</head>
<body>
<div class="container">
            <table class="table table-striped table-hover">
            <thead>
            <tr>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Status</th>
                    <th>Age</th>
                    <th>Address</th>
                    <th>Telephone</th>
                    <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td><c:out value="${user.username}"/></td>
                    <td><c:out value="${user.password}"/></td>
                    <td>
                        <c:choose>
                            <c:when test = "${user.active eq true}">
                                I am a superman
                            </c:when>
                            <c:otherwise>
                                Staying at shadow
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><c:out value="${user.age}"/></td>
                    <td><c:out value="${user.address}"/></td>
                    <td><c:out value="${user.telephone}"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/users/delete">
                        <input type="hidden" name="id" value="${user.id}">
                        <input type="submit" class="btn btn-outline-danger" value="Delete" method="get"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            </table>
            <br>
            <br>
    <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/users/add" role="button">Add new user</a>
</div>
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
<html>