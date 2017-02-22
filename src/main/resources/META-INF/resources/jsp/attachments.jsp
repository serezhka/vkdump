<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Attachments"/>
</jsp:include>

<div class="pages"></div>

<%--@elvariable id="attachments" type="org.springframework.data.domain.Page<com.github.serezhka.vkdump.dao.entity.AttachmentEntity>"--%>
<c:forEach items="${attachments.content}" var="attachment">

    <%--@elvariable id="attachment" type="com.github.serezhka.vkdump.dao.entity.AttachmentEntity"--%>
    <c:if test="${attachment.type eq 'photo'}">
        <c:set var="photo" value="${attachment.photo}" scope="request"/>
        <jsp:include page="single-photo.jsp"/>
    </c:if>

    <c:if test="${attachment.type eq 'video'}">

        <%--@elvariable id="video" type="com.github.serezhka.vkdump.dao.entity.VideoEntity"--%>
        <c:set var="video" value="${attachment.video}"/>

        <img src="${video.photo130}" width="128" height="128">

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
    <c:set var="size" value="1000"/>
</c:if>

<script>
    $(function () {
        $(".pages").pagination({
            currentPage: ${page},
            itemsOnPage: ${size},
            items: ${attachments.totalElements},
            cssStyle: 'dark-theme',
            hrefTextPrefix: "/attachments?type=${param.type}&size=${size}&page="
        });
    });
</script>