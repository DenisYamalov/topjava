const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
};

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (dateTime, type, row) {
                        if (type === "display") {
                            let d = new Date(dateTime);
                            Number.prototype.padLeft = function (base, chr) {
                                const len = (String(base || 10).length - String(this).length) + 1;
                                return len > 0 ? new Array(len).join(chr || '0') + this : this;
                            }
                            return d.getFullYear() + "-" + (d.getMonth() + 1).padLeft() + "-" + d.getDate().padLeft() + " " + d.getHours() + ":" + d.getMinutes().padLeft();
                        }
                        return dateTime;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-meal-excess", data.excess);
            }
        })
    );
});

$('#dateTime').datetimepicker({
    format: 'Y-m-d\\TH:i',
    validateOnBlur: false
});

$('#startDate').datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
    onShow: function (ct) {
        this.setOptions({
            maxDate: $('#endDate').val() ? $('#endDate').val() : false
        });
    }
});

$('#endDate').datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
    onShow: function (ct){
        this.setOptions({
            minDate: $('#startDate').val() ? $('#startDate').val() : false
        });
    }
});

$('#startTime').datetimepicker({
    datepicker: false,
    format: 'H:i',
    onShow: function (ct) {
        this.setOptions({
            maxTime: $('#endTime').val() ? $('#endTime').val() : false
        });
    }
});

$('#endTime').datetimepicker({
    datepicker: false,
    format: 'H:i',
    onShow: function (ct) {
        this.setOptions({
            minTime: $('#startTime').val() ? $('#startTime').val() : false
        });
    }
});