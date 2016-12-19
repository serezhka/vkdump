<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageName" value="Dialogs"/>
</jsp:include>

<jsp:include page="users.jsp"/>

<script type="text/javascript">

    $(document).ready(function () {
        $('#dialogs').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": "/getDialogs",
            "columns": [
                {
                    "data": "message.user_id",
                    "render": function (userId) {
                        if (userId && users[userId]) {
                            var user = users[userId];
                            return '<img src="' + user.photo200Orig + '" width="32" height="32"><br>' + user.firstName + ' ' + user.lastName;
                        } else
                            return 'Chat';
                    }
                },
                {"data": "message.body"},
                {
                    "data": "message.date",
                    "render": function (date) {
                        return timeConverter(date);
                    }
                },
                {
                    "data": null,
                    "render": function (data, type, row) {
                        return '<a href="/messages/' + (row.message.user_id ? row.message.user_id : 2000000000 + row.message.chat_id) + '">View dialog</a>';
                    }
                },
                {
                    "data": null,
                    "render": function (data, type, row) {
                        return '<a href="/attachments?dialog_id=' + (row.message.user_id ? row.message.user_id : 2000000000 + row.message.chat_id) + '&type=photo&page=0&size=500">Photos</a>';
                    }
                }
            ]
        });
    });
</script>

<table id="dialogs" class="display" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th>User ID</th>
        <th>Last message</th>
        <th>Date</th>
        <th>Link</th>
        <th>Photos</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <th>User ID</th>
        <th>Last message</th>
        <th>Date</th>
        <th>Link</th>
        <th>Photos</th>
    </tr>
    </tfoot>
</table>

<jsp:include page="footer.jsp"/>
