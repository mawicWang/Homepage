$(document).ready(function () {
    /**** markdown config**/
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
    };
    /** markdown config end **/

    $("#articleContent").html(marked($('#markdown').val(), {renderer: renderer}));
    var markdownTitle = $("h1:eq(0)", $("#articleContent"));
    console.log($("#title h1"));
    if (markdownTitle.html() === $("#title h1").html() && $('#markdown').val().trim().startsWith("# " + markdownTitle.html())) {
        markdownTitle.remove();
    }

});
