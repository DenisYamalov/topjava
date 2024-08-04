const mealAjaxUrl = "meals/";
const mealAjaxFilterUrl = "meals/filter";

const ctx = {
    ajaxUrl: mealAjaxUrl,
    ajaxFilterUrl: mealAjaxFilterUrl
};

const filterForm = $("#filterForm");

// $(document).ready(function () {
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
    $.get(mealAjaxFilterUrl,
        filterForm.serialize(),
        function (data) {
        console.log(data)
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}