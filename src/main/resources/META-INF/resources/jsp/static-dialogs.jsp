<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>
<% pageContext.setAttribute("quoteChar", "'"); %>

<!DOCTYPE html>
<html>
<head>
    <title>Dialogs</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="../css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="../css/simplePagination.css">
    <script src="../js/jquery-3.1.1.min.js"></script>
    <script src=../js/jquery.dataTables.min.js></script>
    <%-- https://datatables.net/plug-ins/pagination/input --%>
    <script src="../js/jquery.dataTables.input.js"></script>
    <script src="../js/jquery.simplePagination.js"></script>
    <script src="../js/users.js"></script>
</head>
<body>

<%--@elvariable id="dialogs" type="org.springframework.data.domain.Page<com.github.serezhka.vkdump.dao.entity.MessageEntity>"--%>

<script type="text/javascript">
    $(document).ready(function () {
        var dialogsTable = $('#dialogs').DataTable({
            "paging": false,
            "ordering": false,
            "searching": false,
            "info": false
        });

        <c:forEach items="${dialogs.content}" var="dialog">

        <%-- stub --%>
        <c:set var="tBody" value="${fn:replace(dialog.body, newLineChar, '<br/>')}"/>
        <c:set var="tBody" value="${fn:replace(tBody, quoteChar, '`')}"/>

        dialogsTable.row.add([
            tUser(parseInt("${dialog.dialogId}")),
            '${tBody}',
            timeConverter(parseInt("${dialog.date}")),
            tLink(parseInt("${dialog.dialogId}")),
            tPhotos(parseInt("${dialog.dialogId}"))
        ]).draw();

        </c:forEach>

        function tUser(dialogId) {
            if (dialogId < 2000000000 && users[dialogId]) {
                var user = users[dialogId];
                return '<img src="' + user.photo200Orig + '" width="32" height="32"><br>' + user.firstName + ' ' + user.lastName;
            } else
                return 'Chat';
        }

        function tLink(dialogId) {
            var savedDialogs = [143434490, 2000000080, 2000000079, 53064364, 268761473, 104524516, 16546022, 23898368, 13286989, 365611271, 29118960, 56496933, 15630391, 190127001, 2000000007, 93281816, 185416547, 24412964, 66340532, 311599511, 245928094, 186789642, 63748113, 2000000066, 18461732, 34357858, 77549139, 2000000097, 38173515, 31659, 12306721, 1490154, 11036568, 2000000099, 177502823, 2000000098, 54962337, 12887188, 6952716, 71263198, 30067737, 200004722, 6662601, 5013552, 78503645, 8746242, 163477214, 6582976, 225952006, 191462377, 3060334, 2000000096, 23893074, 2000000094, 367223360, 279380501, 104570574, 10755716, 2000000095, 39606028, 62971210, 2000000092, 2000000091, 2000000081, 253768861, 3669242, 2000000090, 81340908, 145564487, 193056522, 299199654, 2000000088, 37120678, 2000000087, 2000000086, 2000000076, 2000000084, 2000000069, 75381006, 6591144, 19798274, 76905739, 9283757, 41486525, 32348447, 75019800, 2000000056, 94901476, 2000000082, 2000000072, 19043975, 2000000078, 30010249, 10284375, 21373242, 160970469, 51860527, 47417400, 2000000077, 2000000074, 136557989, 105289876, 116124461, 11915276, 66057239, 46210210, 138042321, 177344307, 4422686, 151800267, 2000000073, 64434375, 72581078, 144586048, 75371253, 223157245, 2000000063];
            if (savedDialogs.indexOf(dialogId) >= 0) {
                return '<a href="../messages/' + dialogId + '/messages' + dialogId + '-1.html" target="_blank">View dialog</a>'
            } else {
                return 'In a few days'
            }
        }

        function tPhotos(dialogId) {
            var savedDialogs = [143434490, 2000000080, 2000000079, 53064364, 268761473, 104524516, 16546022, 23898368, 13286989, 365611271, 29118960, 56496933, 15630391, 190127001, 2000000007, 93281816, 185416547, 24412964, 66340532, 311599511, 245928094, 186789642, 63748113, 2000000066, 18461732, 34357858, 77549139, 2000000097, 38173515, 31659, 12306721, 1490154, 11036568, 2000000099, 177502823, 2000000098, 54962337, 12887188, 6952716, 71263198, 30067737, 200004722, 6662601, 5013552, 78503645, 8746242, 163477214, 6582976, 225952006, 191462377, 3060334, 2000000096, 23893074, 2000000094, 367223360, 279380501, 104570574, 10755716, 2000000095, 39606028, 62971210, 2000000092, 2000000091, 2000000081, 253768861, 3669242, 2000000090, 81340908, 145564487, 193056522, 299199654, 2000000088, 37120678, 2000000087, 2000000086, 2000000076, 2000000084, 2000000069, 75381006, 6591144, 19798274, 76905739, 9283757, 41486525, 32348447, 75019800, 2000000056, 94901476, 2000000082, 2000000072, 19043975, 2000000078, 30010249, 10284375, 21373242, 160970469, 51860527, 47417400, 2000000077, 2000000074, 136557989, 105289876, 116124461, 11915276, 66057239, 46210210, 138042321, 177344307, 4422686, 151800267, 2000000073, 64434375, 72581078, 144586048, 75371253, 223157245, 2000000063];
            if (savedDialogs.indexOf(dialogId) >= 0) {
                return '<a href="../photos/' + dialogId + '/photos' + dialogId + '-1.html" target="_blank">Photos</a>'
            } else {
                return 'In a few days'
            }
        }
    });
</script>

<div class="pages"></div>

<table id="dialogs" class="display" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th>User</th>
        <th>Last message</th>
        <th>Date</th>
        <th>Link</th>
        <th>Photos</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <th>User</th>
        <th>Last message</th>
        <th>Date</th>
        <th>Link</th>
        <th>Photos</th>
    </tr>
    </tfoot>
</table>

<div class="pages"></div>

<c:set var="page" value="${param.page}"/>
<c:if test="${empty page}">
    <c:set var="page" value="0"/>
</c:if>

<c:set var="size" value="${param.size}"/>
<c:if test="${empty size}">
    <c:set var="size" value="20"/>
</c:if>

<script>
    $(function () {
        $(".pages").pagination({
            currentPage: ${page},
            itemsOnPage: ${size},
            items: ${dialogs.totalElements},
            cssStyle: 'dark-theme',
            hrefTextPrefix: "dialogs-",
            hrefTextSuffix: ".html"
        });
    });
</script>

<jsp:include page="footer.jsp"/>