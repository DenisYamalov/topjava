const mealAjaxUrl = "ui/meals/";
const mealAjaxFilterUrl = mealAjaxUrl + "filter";

const ctx = {
    ajaxUrl: mealAjaxUrl,
    ajaxFilterUrl: mealAjaxFilterUrl
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
    $.ajax({
        url: ctx.ajaxFilterUrl,
        method: "get",
        dataType: "html",
        data: filterForm.serialize()
    }).done(function (data, textStatus, jqXHR) {
            console.log(data);
            console.log(textStatus);
            console.log(jqXHR);
            ctx.datatableApi.clear().rows.add(data).draw();
        }
    );
}

$("#filter").click(function (event) {
    event.preventDefault();
    getFiltered();
    return false;
})