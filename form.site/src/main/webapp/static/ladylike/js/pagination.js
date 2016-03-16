var url = window.location.pathname;
var page = 2; // Start from 2nd page, 1st is already rendered by JSP.
var container = $("#st_container");
var loader = $("<h2>loading ...</h2>");
var btn = $("<div class='more'><a class='btn' href='javascript:void(0);' ontouchstart=''>ΠΕΡΙΣΣΟΤΕΡΑ</a></div>");
$(document).ready(function() {
    $.ajax({
        url: url + "?page=" + page,
        dataType: "html",
        success: function(data) {
            var art_count = 0;
            var stories = $(data).find("#st_container").children();
            if (stories.length > 0) {
                container.append(btn);
                if ($(this).hasClass("row")) {
                    art_count++;
                }
            }
            if(art_count > 0) {
                container.append(btn);
            }
        }
    });
});
var handler = function(callback) {
    $.ajax({
        url: url + "?page=" + page,
        dataType: "html",
        success: function(data) {
        	var art_count = 0;
            var stories = $(data).find("#st_container").children();
            if(stories.length > 0) {
                container.append(loader);
                var anchor = "page" + page;
                $(stories).each(function() {
                	if($(this).hasClass("no-more")) {
                		$(this).html("");
                	}
                });
                container.append(stories);
                container.append(btn);
                window.location.hash = anchor;
                if($(this).hasClass("row")) {
                    art_count++;
                }
            }
            if(art_count == 0) {
            	btn.hide();
            }
            page++;
            loader.remove();
            $(".lazyload").lazyload({
            	threshold: 200,
            	effect: 'fadeIn',
            	placeholder: "${defaultImage}" /*Application Scope Parameter*/
            });
        }
    });
};
var recursiveLoad = function(targetPage) {
    return function() {
        if(page <= targetPage) {
        	handler(recursiveLoad(targetPage));
        }
    };
};
btn.bind('click', handler);
if(window.location.hash) {
    var hash = window.location.hash.substring(1);
    if(hash.indexOf("page") > -1) {
        targetPage = parseInt(hash.substring(4));
        handler(recursiveLoad(targetPage));
    }
}