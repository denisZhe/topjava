var mainAjaxUrl = 'ajax/admin/users/';
var ajaxUrl = mainAjaxUrl;
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $('#datatable').DataTable({
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
    });
    makeEditable();
});

function activeChange(id) {
    var checkbox = $('tr#' + id).find('input[type=checkbox]');
    var checked = checkbox.is(':checked');
    $.ajax({
        type: "POST",
        url: mainAjaxUrl + id,
        data: {"enabled": checked},
        success: function () {
            successNoty('Active changed');
            if (!checked) {
                $('tr#' + id).css('opacity', 0.7);
            } else {
                $('tr#' + id).css('opacity', 1);
            }
        },
        error: function () {
            checkbox.prop('checked', !checked);
        }
    });
}