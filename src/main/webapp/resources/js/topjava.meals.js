const mealAjaxUrl = "ui/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl,
    ajaxFilterUrl: mealAjaxUrl + "filter"
};

const filterForm = $("#filterForm");

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
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
                    "desc"
                ]
            ]
        })
    );
});

function getFiltered() {
    $.get(ctx.ajaxFilterUrl, filterForm.serialize(), function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}

$("#filter").click(function (event) {
    event.preventDefault();
    getFiltered();
    return false;
})