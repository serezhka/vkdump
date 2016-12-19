<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--@elvariable id="users" type="java.util.List<com.github.serezhka.vkdump.dto.UserDTO>"--%>

<script type="text/javascript">

    var users = {
        <c:forEach items="${users}" var="user">
        <c:set var="firstName" value="${fn:replace(user.firstName, \"'\", \"\")}"/>
        <c:set var="lastName" value="${fn:replace(user.lastName, \"'\", \"\")}"/>
        '${user.id}': {
            'firstName': '${firstName}',
            'lastName': '${lastName}',
            'photo200Orig': '${user.photo200Orig}'
        },
        </c:forEach>
    };

</script>
