<%-- 
    Document   : users
    Created on : 9-Mar-2023, 3:12:45 PM
    Author     : Mitchell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
    </head>
    <body>
        <h1>Manage Users</h1>
        <p style="color:red;">${message}</p>
        <table>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Role</th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <c:choose>
                        <c:when test="${user.getRole().getRoleID() == 1}">
                            <td>Admin</td>
                        </c:when>
                        <c:otherwise>
                            <td>User</td>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        <c:url value="/User" var="edit">
                            <c:param name="email" value="${user.email}"></c:param>
                            <c:param name="action" value="edit"></c:param>
                        </c:url>
                        <a href="${edit}">Edit</a>
                    </td>
                    <td>
                        <c:url value="/User" var="delete">
                            <c:param name="email" value="${user.email}"></c:param>
                            <c:param name="action" value="delete"></c:param>
                        </c:url>
                        <a href="${delete}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <br>
        <c:if test="${selectUser eq null}">
            <h2>Add User</h2>
            <form action="User" method="post">
                <label>Email:</label>
                <input type="email" name="email" required><br>
                <label>First Name:</label>
                <input type="text" name="firstName" required><br>
                <label>Last Name:</label>
                <input type="text" name="lastName" required><br>
                <label>Password</label>
                <input type="text" name="password" required><br>
                <label>Role:</label>
                <select name="role">
                    <option value="1">Admin</option>
                    <option value="2">User</option>
                </select><br>
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Add User">
            </form>
        </c:if>
        <c:if test="${selectUser ne null}">
            <h2>Edit User</h2>
            <form action="User" method="post">
                <input type="hidden" name="email" value="${selectUser.email}">
                <label>Email: ${selectUser.email}</label>
                <br>
                <label>First Name:</label>
                <input type="text" name="firstName" value="${selectUser.firstName}" required><br>
                <label>Last Name:</label>
                <input type="text" name="lastName" value="${selectUser.lastName}" required><br>
                <label>Password</label>
                <input type="text" name="password" value="${selectUser.password}" required><br>
                <label>Role:</label>
                <select name="role">
                    <c:choose>
                        <c:when test="${selectUser.getRole().getRoleID() == 1}">
                            <option value="1" selected>Admin</option>
                            <option value="2">User</option>
                        </c:when>
                        <c:otherwise>
                            <option value="1">Admin</option>
                            <option value="2" selected>User</option>
                        </c:otherwise>
                    </c:choose>
                </select><br>
                <input type="hidden" name="action" value="edit">
                <input type="submit" value="Edit User">
                <br>
                <br>
                <a href="User" class="button">Cancel</a>
            </form>
        </c:if>
    </body>
</html>
