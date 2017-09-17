$(function () {
    marked.setOptions({
        highlight: function (code) {
            return hljs.highlightAuto(code).value;
        },
        gfm: true,
        tables: true
    });

    // markdown render
    var renderer = new marked.Renderer();
    renderer.table = function (header, body) {
        return '<table class="table table-bordered table-hover">\n'
            + '<thead>\n'
            + header
            + '</thead>\n'
            + '<tbody>\n'
            + body
            + '</tbody>\n'
            + '</table>\n';
    }

    $('#content')
        .bind('input change', function (e) {
            $('#preview').html(marked($('#content').val(), {renderer: renderer}));
        })
        .trigger('input');
    $("#resizeFull").on("click", function () {
        $(".editor-wrapper").toggleClass("hide");
        $("#fullPreview").html($('#preview').html());
        $("#fullPreview").toggleClass("hide");
    })
});
