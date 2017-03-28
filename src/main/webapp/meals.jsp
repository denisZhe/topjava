<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
    <title>Meal List</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Список еды</h2>

<p><a href="<c:url value="meals"><c:param name="action" value="addMeal"/></c:url>">Добавить</a></p>
<br>

<table>
    <tr>
        <th width="328">Дата и время</th>
        <th width="259">Описание</th>
        <th width="234">Калории</th>
        <th width="86">Редактирование</th>
        <th width="87">Удаление</th>
    </tr>
    <c:forEach var="mealWithExceed" items="${requestScope.mealWithExceedList}">
        <tr class="${mealWithExceed.exceed ? "exceeded" : "normal"}">
            <td>${fn:replace(mealWithExceed.dateTime,"T" ," ")}</td>
            <td>${mealWithExceed.description}</td>
            <td>${mealWithExceed.calories}</td>
            <td align="center">
                <a href="<c:url value="edit"><c:param name="edit_id" value="${mealWithExceed.id}"/></c:url>">Редактировать</a>
            </td>
            <td align="center">
                <a href="<c:url value="meals"><c:param name="remove_id" value="${mealWithExceed.id}"/></c:url>">Удалить</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>