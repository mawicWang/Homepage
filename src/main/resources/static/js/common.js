function notImplemented() {
    alert("not Implemented");
}

function changeTheme() {
    (function (e, a, g, h, f, c, b, d) {
        if (!(f = e.jQuery) || g > f.fn.jquery || h(f)) {
            c = a.createElement("script");
            c.type = "text/javascript";
            c.src = "//ajax.googleapis.com/ajax/libs/jquery/" + g + "/jquery.min.js";
            c.onload = c.onreadystatechange = function () {
                if (!b && (!(d = this.readyState) || d == "loaded" || d == "complete")) {
                    h((f = e.jQuery).noConflict(1), b = 1);
                    f(c).remove()
                }
            };
            a.documentElement.childNodes[0].appendChild(c)
        }
    })(window, document, "1.3.2", function ($, L) {
        if ($(".bootswatcher")[0]) {
            $(".bootswatcher").remove()
        } else {
            var $e = $('<select class="bootswatcher"><option>Cerulean</option><option>Cosmo</option><option>Cyborg</option><option>Darkly</option><option>Flatly</option><option>Journal</option><option>Lumen</option><option>Paper</option><option>Readable</option><option>Sandstone</option><option>Simplex</option><option>Slate</option><option>Spacelab</option><option>Superhero</option><option>United</option><option>Yeti</option></select>');
            var l = 8;
            // var l = 1 + Math.floor(Math.random() * $e.children().length);
            $e.css({
                "z-index": "99999",
                position: "fixed",
                top: "5px",
                left: "5px",
                opacity: "0.5",
                color: "#000"
            }).hover(function () {
                $(this).css("opacity", "1")
            }, function () {
                $(this).css("opacity", "0.5")
            }).change(function () {
                if (!$("link.bootswatcher")[0]) {
                    $("head").append('<link rel="stylesheet" class="bootswatcher">')
                }
                $("link.bootswatcher").attr("href", "//bootswatch.com/" + $(this).find(":selected").text().toLowerCase() + "/bootstrap.min.css")
            }).find("option:nth-child(" + l + ")").attr("selected", "selected").end().trigger("change");
            $("body").append($e)
        }
    });
}

function clearErrorMessage(selector) {
    $(selector).find(".btn-msg-close").click();
}

function getAlertContent(msg) {
    var content = $("<div id=\"loginErrMsg\" class=\"alert alert-danger alert-dismissible fade in\" role=\"alert\">\n" +
        "                <button type=\"button\" class=\"close btn-msg-close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
        "                    <span aria-hidden=\"true\">&times;</span></button>\n" +
        "                        " + msg + "\n" +
        "            </div>")

    return content;
}
