<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Voices</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="../css/jquery.dataTables.min.css">
    <script src="../js/jquery-3.1.1.min.js"></script>
    <script src=../js/jquery.dataTables.min.js></script>
    <script src="../js/jquery.dataTables.input.js"></script>
    <script src="../js/users.js"></script>
</head>
<body>

<%--@elvariable id="attachments" type="org.springframework.data.domain.List<com.github.serezhka.vkdump.dao.entity.AttachmentEntity>"--%>

<script type="text/javascript">
    $(document).ready(function () {
        var dialogsTable = $('#voices').DataTable({
            "paging": false,
            "ordering": false,
            "searching": false,
            "info": false
        });

        <c:forEach items="${attachments}" var="attachment">

        dialogsTable.row.add([
            tUser(parseInt("${attachment.doc.ownerId}")),
            '<audio controls><source src="${attachment.doc.url}"></audio>',
            timeConverter(parseInt("${attachment.doc.date}"))
        ]).draw();

        </c:forEach>

        function tUser(dialogId) {
            if (dialogId < 2000000000 && users[dialogId]) {
                var user = users[dialogId];
                return '<img src="' + user.photo200Orig + '" width="32" height="32"><br>' + user.firstName + ' ' + user.lastName;
            } else
                return 'Chat';
        }
    });
</script>

<table id="voices" class="display" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th>From</th>
        <th>Voice</th>
        <th>Date</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <th>From</th>
        <th>Voice</th>
        <th>Date</th>
    </tr>
    </tfoot>
</table>

<jsp:include page="footer.jsp"/>