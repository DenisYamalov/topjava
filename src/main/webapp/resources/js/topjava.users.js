const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function enabled(checkboxElem) {
    let row = $(checkboxElem).parents('tr');
    let setEnabled = !!checkboxElem.checked;
    setEnabledOnServer(row.attr('id'), setEnabled);
    row.attr('enabled', setEnabled);
}

function setEnabledOnServer(userId, setEnabled) {
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + "enable/" + userId,
        data: {enabled: setEnabled}
    }).done(function () {
    });
}

