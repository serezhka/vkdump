<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Photos</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="../../css/simplePagination.css">
    <script src="../../js/jquery-3.1.1.min.js"></script>
    <script src="../../js/jquery.simplePagination.js"></script>
</head>
<body>

<%--@elvariable id="dialogId" type="java.lang.Integer"--%>
<%--@elvariable id="attachments" type="org.springframework.data.domain.Page<com.github.serezhka.vkdump.dao.entity.AttachmentEntity>"--%>
<c:forEach items="${attachments.content}" var="attachment">

<c:if test="${attachment.type eq 'photo'}">
    <c:set var="photo" value="${attachment.photo}" scope="request"/>
    <jsp:include page="single-photo.jsp"/>
</c:if>

</c:forEach>

<div class="pages"></div>

<jsp:include page="footer.jsp"/>

<c:set var="page" value="${param.page}"/>
<c:if test="${empty page}">
    <c:set var="page" value="1"/>
</c:if>

<c:set var="size" value="${param.size}"/>
<c:if test="${empty size}">
    <c:set var="size" value="100"/>
</c:if>

<script>
    $(function () {
        $(".pages").pagination({
            currentPage: ${page},
            itemsOnPage: ${size},
            items: ${attachments.totalElements},
            cssStyle: 'dark-theme',
            hrefTextPrefix: "photos${dialogId}-",
            hrefTextSuffix: ".html"
        });
    });
</script>