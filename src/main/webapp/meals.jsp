<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>

    <form method="post" action="meals">
        <input type="hidden" name="action" value="filtered">
        <div>
            <div style="float: left; margin-right: 10px">
                с даты<br>
                <input type="date" name="startDate" value="${requestScope.startDate}"><br>
                по дату<br>
                <input type="date" name="endDate" value="${requestScope.endDate}"><br>
            </div>
            <div style="float: left; margin-right: 10px">
                с времени<br>
                <input type="time" name="startTime" value="${requestScope.startTime}"><br>
                по время<br>
                <input type="time" name="endTime" value="${requestScope.endTime}"><br>
            </div>
            <div>
                <br><br><br><br><br><br>
                <button type="submit">Фильтровать</button>
            </div>
        </div>
    </form>
    <div>
        <button onclick="window.location.href='<c:url value="meals"><c:param name="action" value="all"/></c:url>'">Весь список</button>
    </div>

    <hr>

    <a href="meals?action=create">Add Meal</a>
    <hr>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>