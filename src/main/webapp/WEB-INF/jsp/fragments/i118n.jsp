<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
    const i18n = {}; // https://learn.javascript.ru/object
    <c:forEach var="key"
               items='${["common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","common.confirm"]}'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
    </c:forEach>

<%--    <c:set var="addTitleMeal">--%>
<%--    <spring:message code="meal.add"/>--%>
<%--    </c:set>--%>
<%--    <c:set var="editTitleMeal">--%>
<%--    <spring:message code="meal.edit"/>--%>
<%--    </c:set>--%>

<%--    <c:set var="addTitleUser">--%>
<%--    <spring:message code="user.add"/>--%>
<%--    </c:set>--%>
<%--    <c:set var="editTitleUser">--%>
<%--    <spring:message code="user.edit"/>--%>
<%--    </c:set>--%>

<%--    i18n["addTitle"] = "${param.addTitle}";--%>
<%--    i18n["editTitle"] = "${param.editTitle}";    --%>

    i18n["addTitle"] = '<spring:message code="${param.page}.add"/>';
    i18n["editTitle"] = '<spring:message code="${param.page}.edit"/>';
</script>