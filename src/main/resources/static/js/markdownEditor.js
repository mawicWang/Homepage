$(function () {
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
    var firstH1 = true;
    renderer.heading = function (text, level, raw) {
        if (firstH1 && level == 1) {
            $("#title").val(text);
            firstH1 = false;
        }
        return '<h'
            + level
            + ' id="'
            + renderer.options.headerPrefix
            + raw.toLowerCase().replace(/[^\w]+/g, '-')
            + '">'
            + text
            + '</h'
            + level
            + '>\n';
    };
    var firstParagraph = true;
    renderer.paragraph = function (text) {
        if (firstParagraph) {
            $("#outline").val(text);
            firstParagraph = false;
        }
        return '<p>' + text + '</p>\n';
    };

    $('#content')
        .bind('input change', function (e) {
            // 重新初始化标题和简介识别
            firstH1 = true;
            firstParagraph = true;
            // markdown 编译
            $('#preview').html(marked($('#content').val(), {renderer: renderer}));
        })
        .trigger('input');
    $("#resizeFull").on("click", function () {
        $(".editor-wrapper").toggleClass("hide");
        $("#fullPreview").html($('#preview').html());
        $("#fullPreview").toggleClass("hide");
    })
    /** markdown config end **/

    /** tag input component **/
    var $container = $(".tag-input");
    $container.data("tagList", []);

    function initTagInput() {
        var tags = $("#listTagStr").val();
        if (tags && tags !== '') {
            $.each(tags.split(','), function (i, item) {
                addTag(item);
            });
        }
    }

    function addTag(tag) {
        // trim
        tag = $.trim(tag);
        // check null
        if (!tag || tag === '') {
            return
        }
        // check exists
        if ($.inArray(tag, $container.data("tagList")) !== -1) {
            // get the exist one
            var exists = $(".tag", $container).filter(function () {
                return $(this).data("tagName") === tag;
            })[0];
            // blink once if exists
            exists.hide().fadeIn();
            return
        }
        // init tag
        var $tag = $("<span class=\"tag label label-info\">" + tag + " <span class=\"glyphicon glyphicon-remove\"></span></span>");
        // add tag
        $("input", $container).before($tag);
        // save tag name in Jquery cache
        $tag.data("tagName", tag);
        // save to list
        $container.data("tagList").push(tag);
        console.log($container.data("tagList"));
    }

    initTagInput();
    $container.on("blur keypress", "input", function (e) {
        if (e.type === 'keypress') {
            if (e.which !== 13) {
                return
            }
            e.preventDefault();
        }
        var $input = $(e.target);
        addTag($input.val());
        $input.val('');
    });
    $container.on("click", ".tag", function (e) {
        // get the tag
        var t = $(e.target).closest(".tag");
        // get tag name
        var tagName = t.data("tagName");
        // remove from tag list
        var filterList = $.grep($container.data("tagList"), function (item) {
            return item !== tagName;
        });
        $container.data("tagList", filterList);
        // remove tag DOM
        t.remove();
        console.log($container.data("tagList"));

    });
    /** tag inpput component end **/

    // form validator
    var saveAjaxPost;
    $('#markdownForm').formValidation({
        //message: 'This value is not valid',
        locale: 'zh_CN',
        excluded: ':disabled',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            title: {
                validators: {
                    notEmpty: {
                        message: '请输入标题'
                    },
                    stringLength: {
                        min: 1,
                        max: 100
                    }
                }
            },
            outline: {
                validators: {
                    stringLength: {
                        max: 200
                    }
                }
            },
            authorId: {
                validators: {
                    notEmpty: {
                        message: '请选择作者'
                    }
                }
            },
            categoryId: {
                validators: {
                    notEmpty: {
                        message: '请选择分类'
                    }
                }
            }
        }
    })
        .on('success.form.fv', function (e) {
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // and the FormValidation instance
            var fv = $form.data('formValidation');

            // Do whatever you want here ...
            $("#btnSaveBlogSubmit").button("loading");
            $("#articleContent").val($("#content").val());
            $("#listTagStr").val($container.data("tagList").join(','));

            // Use the defaultSubmit() method if you want to submit the form
            // See http://formvalidation.io/api/#default-submit
            fv.defaultSubmit();
        });
});
