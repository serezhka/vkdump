<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Attachments"/>
</jsp:include>

<%--@elvariable id="attachments" type="org.springframework.data.domain.Page<com.github.serezhka.vkdump.dto.AttachmentDTO>"--%>
<c:forEach items="${attachments.content}" var="attachment">

    <%--@elvariable id="attachment" type="com.github.serezhka.vkdump.dto.AttachmentDTO"--%>
    <c:if test="${attachment.type eq 'photo'}">

        <%--@elvariable id="photo" type="com.github.serezhka.vkdump.dto.PhotoDTO"--%>
        <c:set var="photo" value="${attachment.photo}"/>

        <c:set var="smallLink" value="${photo.photo130}"/>

        <%-- Select biggest and smallest source link --%>

        <c:if test="${not empty photo.photo130}">
            <c:set var="bigLink" value="${photo.photo130}"/>
        </c:if>

        <c:if test="${not empty photo.photo604}">
            <c:if test="${empty smallLink}">
                <c:set var="smallLink" value="${photo.photo604}"/>
            </c:if>
            <c:set var="bigLink" value="${photo.photo604}"/>
        </c:if>

        <c:if test="${not empty photo.photo807}">
            <c:if test="${empty smallLink}">
                <c:set var="smallLink" value="${photo.photo807}"/>
            </c:if>
            <c:set var="bigLink" value="${photo.photo807}"/>
        </c:if>

        <c:if test="${not empty photo.photo1280}">
            <c:if test="${empty smallLink}">
                <c:set var="smallLink" value="${photo.photo1280}"/>
            </c:if>
            <c:set var="bigLink" value="${photo.photo1280}"/>
        </c:if>

        <c:if test="${not empty photo.photo2560}">
            <c:if test="${empty smallLink}">
                <c:set var="smallLink" value="${photo.photo2560}"/>
            </c:if>
            <c:set var="bigLink" value="${photo.photo2560}"/>
        </c:if>

        <a href="${bigLink}" target="_blank"><img src="${smallLink}" width="128" height="128"></a>

    </c:if>

</c:forEach>

<jsp:include page="footer.jsp"/>