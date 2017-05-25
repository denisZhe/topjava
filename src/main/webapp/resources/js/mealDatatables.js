var mainAjaxUrl = 'ajax/profile/meals/';
var ajaxUrl = mainAjaxUrl;
var datatableApi;

$(function () {
    datatableApi = $('#datatable').DataTable({
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
    });
    makeEditable();
});

function filterMeal() {
    var form = $("#filterForm");
    $.ajax({
        url: $(form).attr('action'),
        type: $(form).attr('method'),
        data: $(form).serialize(),
        success: function (data) {
            ajaxUrl = mainAjaxUrl + '?' + $(form).serialize();
            datatableApi.clear();
            $.each(data, function (key, item) {
                datatableApi.row.add(item);
            });
            datatableApi.draw();
            successNoty('Filtered');
        }
    });
    return false;
}

function clearFilter() {
    $("#filterForm").find(":input").val(null);
    ajaxUrl = mainAjaxUrl;
    updateTable();
    successNoty('Filter cleared');
}

/*filter meal by get

function filterMeal() {
    $('#filterForm').submit(function () {
        $.ajax({
            url: $(this).attr('action'),
            type: $(this).attr('method'),
            data: $(this).serialize(),
            success: function (data) {
                ajaxUrl = mainAjaxUrl + '?' + $(form).serialize();
                datatableApi.clear();
                $.each(data, function (key, item) {
                    datatableApi.row.add(item);
                });
                datatableApi.draw();
                successNoty('Filtered');
            }
        });
        return false;
    })
}*/
