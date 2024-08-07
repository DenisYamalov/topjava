const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
    updateTable: getFiltered
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

function enable(checkboxElem) {
    let row = $(checkboxElem).parents('tr');
    let setEnabled = checkboxElem.checked;
    enabled(row.attr('id'), setEnabled);
    row.attr('enabled', setEnabled);
}

function enabled(userId, setEnabled) {
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + "enable/" + userId,
        data: {enabled: setEnabled}
    }).done(function () {
    });
}

