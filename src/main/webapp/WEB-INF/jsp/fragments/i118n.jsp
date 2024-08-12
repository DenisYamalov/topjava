<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
const i18n = {}; // https://learn.javascript.ru/object
<c:forEach var="key"
           items='${["common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","common.confirm"]}'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
</c:forEach>
i18n["addTitle"] = sessionStorage.getItem("addTitle");
i18n["editTitle"] = sessionStorage.getItem("editTitle");