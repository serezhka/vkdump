<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Faves"/>
</jsp:include>

<div class="pages"></div>

<div id="faves">

    <%--@elvariable id="faves" type="org.springframework.data.domain.Page<com.github.serezhka.vkdump.dao.online.entity.FaveEntity>"--%>
    <c:forEach items="${faves.content}" var="fave">

        <%--@elvariable id="fave" type="com.github.serezhka.vkdump.dao.online.entity.FaveEntity"--%>
        <c:if test="${fave.type eq 'photo'}">
            <c:set var="photo" value="${fave.photo}" scope="request"/>
            <jsp:include page="single-photo.jsp"/>
        </c:if>

    </c:forEach>

</div>

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
            items: ${faves.totalElements},
            cssStyle: 'dark-theme',
            hrefTextPrefix: "/faves?type=${param.type}&size=${size}&page="
        });
    });
</script>
