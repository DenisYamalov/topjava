const mealAjaxUrl = "meals/ui/";
const mealAjaxFilterUrl = "meals/ui/filter";

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
    $.ajax({
            url: ctx.ajaxFilterUrl,
            method: "get",
            dataType: "html",
            data: filterForm.serialize(),
            // success: function (data) {
            //     return ctx.datatableApi.clear().rows.add(data).draw();
            // }
        }.done(function (data, textStatus, jqXHR) {
            console.log(textStatus);
            console.log(jqXHR);
            ctx.datatableApi.clear().rows.add(data).draw();
        })
    );
}