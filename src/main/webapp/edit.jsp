<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
    <title>Meal</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2><a href="meals">Meal List</a></h2>
<br/>
<h2>${(requestScope.action == "addMeal") ? "Добавить еду" : "Редактировать еду"}</h2>

<form action="meals" method="post">
    <input type="hidden" name="id" value="${requestScope.editedMeal.id}">
    Дата и время<br>
    <input type="datetime-local" required name="dateTime" value="${requestScope.editedMeal.dateTime}"><br>
    Описание<br>
    <input type="text" required name="description" value="${requestScope.editedMeal.description}"><br>
    Калории<br>
    <input type="number" required name="calories" value="${requestScope.editedMeal.calories}"><br>
    <br><input type="submit" value="Сохранить">
</form>
</body>
</html>