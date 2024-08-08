const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
    updateTable: updateTable
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
    let enabled = checkboxElem.checked;
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + "enable/" + row.attr('id'),
        data: {enabled: enabled}
    }).done(function () {
        row.attr('enabled', enabled);
    }).fail(function () {
        $(checkboxElem).prop('checked', !enabled);
    });
}