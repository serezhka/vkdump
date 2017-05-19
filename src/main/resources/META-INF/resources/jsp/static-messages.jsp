<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ju" uri="JsonUtils" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>
<% pageContext.setAttribute("quoteChar", "'"); %>

<!DOCTYPE html>
<html>
<head>
    <title>Messages</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="../../css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="../../css/simplePagination.css">
    <script src="../../js/jquery-3.1.1.min.js"></script>
    <script src=../../js/jquery.dataTables.min.js></script>
    <%-- https://datatables.net/plug-ins/pagination/input --%>
    <script src="../../js/jquery.dataTables.input.js"></script>
    <script src="../../js/jquery.simplePagination.js"></script>
    <script src="../../js/users.js"></script>
</head>
<body>

<%--@elvariable id="dialogId" type="java.lang.Integer"--%>
<%--@elvariable id="messages" type="org.springframework.data.domain.Page<com.github.serezhka.vkdump.dao.entity.MessageEntity>"--%>

<script type="text/javascript">
    $(document).ready(function () {
        var dialogsTable = $('#dialogs').DataTable({
            "paging": false,
            "ordering": false,
            "searching": false,
            "info": false
        });

        <c:forEach items="${messages.content}" var="message">

        <%-- stub --%>
        <c:set var="tBody" value="${fn:replace(message.body, newLineChar, '<br/>')}"/>
        <c:set var="tBody" value="${fn:replace(tBody, quoteChar, '`')}"/>

        dialogsTable.row.add([
            tUser(parseInt("${message.fromId}")),
            '${tBody}', // FIXME Forwarded messages
            timeConverter(parseInt("${message.date}")),
            tAttachments(${ju:stringify(message.attachments)})
        ]).draw();

        </c:forEach>

        function tUser(fromId) {
            if (fromId < 2000000000 && users[fromId]) {
                var user = users[fromId];
                return '<img src="' + user.photo200Orig + '" width="32" height="32"><br>' + user.firstName + ' ' + user.lastName;
            } else
                return 'o_0';
        }

        function tAttachments(attachments) {
            if (attachments) {
                var resultHtml = '';
                attachments.forEach(function (attachment) {
                    switch (attachment.type) {
                        case 'photo':
                            resultHtml += '<a href="' + attachment.photo.photo604 + '" target="_blank"><img src="' + attachment.photo.photo130 + '" width="64" height="64"></a>';
                            break;
                        case 'sticker':
                            resultHtml += '<img src="' + attachment.sticker.photo64 + '" width="64" height="64">';
                            break;
                        case 'gift':
                            resultHtml += '<img src="' + attachment.gift.thumb96 + '" width="64" height="64">';
                            break;
                        case 'audio':
                            resultHtml += '[audio: ' + attachment.audio.title + '], ';
                            break;
                        case 'doc':
                            switch (attachment.doc.type) {
                                case 5:
                                    resultHtml += '<a href="' + attachment.doc.url + '" target="_blank">[voice]</a>';
                                    break;
                                default:
                                    resultHtml += '[doc: ' + attachment.doc.title + '], ';
                                    break;
                            }
                            break;
                        default:
                            resultHtml += attachment.type + ', ';
                            break;
                    }
                });
                return resultHtml;
            } else return '';
        }
    });
</script>

<div class="pages"></div>

<table id="dialogs" class="display" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th>From</th>
        <th>Body</th>
        <th>Date</th>
        <th>Attachments</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <th>From</th>
        <th>Body</th>
        <th>Date</th>
        <th>Attachments</th>
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
    <c:set var="size" value="50"/>
</c:if>

<script>
    $(function () {
        $(".pages").pagination({
            currentPage: ${page},
            itemsOnPage: ${size},
            items: ${messages.totalElements},
            cssStyle: 'dark-theme',
            hrefTextPrefix: "messages${dialogId}-",
            hrefTextSuffix: ".html"
        });
    });
</script>

<jsp:include page="footer.jsp"/>