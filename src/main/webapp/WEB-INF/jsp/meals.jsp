<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><spring:message code="meals.title"/></h3>
            <div class="row">
                <div class="col-sm-7">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <form class="form-horizontal" id="filterForm"
                                  action="<c:url value="ajax/profile/meals/filter"/>"
                                  method="post">
                                <div class="form-group">
                                    <label for="startDate" class="control-label col-sm-3"><spring:message
                                            code="meals.startDate"/></label>
                                    <div class="col-sm-3">
                                        <input type="date" class="form-control" id="startDate" name="startDate"/>
                                    </div>
                                    <label for="startTime" class="control-label col-sm-4"><spring:message
                                            code="meals.startTime"/></label>
                                    <div class="col-sm-2">
                                        <input type="time" class="form-control" id="startTime" name="startTime"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="endDate" class="control-label col-sm-3"><spring:message
                                            code="meals.endDate"/></label>
                                    <div class="col-sm-3">
                                        <input type="date" class="form-control" id="endDate" name="endDate"/>
                                    </div>
                                    <label for="endTime" class="control-label col-sm-4"><spring:message
                                            code="meals.endTime"/></label>
                                    <div class="col-sm-2">
                                        <input type="time" class="form-control" id="endTime" name="endTime"/>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="panel-footer text-right">
                            <a class="btn btn-danger" onclick="clearFilter()">
                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                            </a>
                            <a class="btn btn-primary" onclick="filterMeal()">
                                <span class="glyphicon glyphicon-filter" aria-hidden="true"></span>
                            </a>
                        </div>

                    </div>
                </div>
            </div>
            <a class="btn btn-info" onclick="add()">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            </a>
            <div class="row">
                <div class="col-sm-12">
                    <table class="table table-striped display dataTable no-footer" id="datatable"
                           aria-describedby="datatable_info">
                        <thead>
                        <tr>
                            <th><spring:message code="meals.dateTime"/></th>
                            <th><spring:message code="meals.description"/></th>
                            <th><spring:message code="meals.calories"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <c:forEach items="${meals}" var="meal">
                            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                                <td>${fn:formatDateTime(meal.dateTime)}</td>
                                <td>${meal.description}</td>
                                <td>${meal.calories}</td>
                                <td><a class="btn btn-xs btn-primary" onclick="">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                </a></td>
                                <td><a class="btn btn-xs btn-danger" onclick="deleteRow(${meal.id})">
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                </a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2><spring:message code="${meal.isNew() ? 'meals.add' : 'meals.edit'}"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm" action="<c:url value="/ajax/profile/meals"/>"
                      method="post">
                    <input type="text" hidden="hidden" id="id" name="id">
                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3"><spring:message
                                code="meals.dateTime"/></label>
                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" id="dateTime" name="dateTime">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3"><spring:message
                                code="meals.description"/></label>
                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description" size="40">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3"><spring:message
                                code="meals.calories"/></label>
                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="calories" name="calories">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button class="btn btn-primary" type="button" onclick="save()">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>