<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Messages"/>
</jsp:include>

<jsp:include page="users.jsp"/>

<%--@elvariable id="dialog_id" type="java.lang.Long"--%>

<script type="text/javascript">

    $(document).ready(function () {
        var table = $('#dialogs').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": "/getMessages",
                "data": function (d) {
                    return $.extend({}, d, {
                        "dialog_id": ${dialog_id}
                    });
                }
            },
            "columns": [
                {
                    "data": "from_id",
                    "render": function (fromId) {
                        if (fromId && users[fromId]) {
                            var user = users[fromId];
                            return '<img src="' + user.photo200Orig + '" width="32" height="32">' + user.firstName + ' ' + user.lastName;
                        } else
                            return '0_o';
                    }
                },

                {"data": "body"},
                {
                    "data": "date",
                    "render": function (date) {
                        return timeConverter(date);
                    }
                },
                {
                    "data": null,
                    "render": function (data, type, row) {
                        if (row.attachments) {
                            var resultHtml = '';
                            row.attachments.forEach(function (attachment) {
                                switch (attachment.type) {
                                    case 'photo':
                                        resultHtml += '<img src="' + attachment.photo.photo_130 + '" width="64" height="64">';
                                        break;
                                }
                            });
                            return resultHtml;
                        } else return '';
                    }
                }
            ],
            "createdRow": function (row, data) {
                if (data.fwd_messages) {
                    var tableRow = table.row(row);
                    tableRow.child(fwdMessagesTable(data.fwd_messages)).show();
                }
            }
        });
    });

    function fwdMessagesTable(fwdMessages) {
        var table = '<table style="margin-left: 50px"><tbody>';

        fwdMessages.forEach(function (message) {
            table += '<tr>';
            table += '<td><img src="' + users[message.user_id] + '" width="32" height="32"></td>';
            table += '<td>' + message.body + '</td>';
            table += '</tr>';
        });

        table += '</tbody></table>';
        return table;
    }

</script>

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

<jsp:include page="footer.jsp"/>
