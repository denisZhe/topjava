$(function () {
    var path = window.location.pathname;
    var params = window.location.search;
    var otherParams = params;
    var pathEn = path;
    var pathRu = path;
    if (params.indexOf("lang") === -1) {
        if (params.length > 0) {
            pathEn = path + params + "&lang=en";
            pathRu = path + params + "&lang=ru";
        } else {
            pathEn = path + "?lang=en";
            pathRu = path + "?lang=ru";
        }
    } else if ($("#locale").text() === "en") {
        if (params.length > 0) {
            otherParams = params.replace("&lang=en", "");
            if (otherParams > 0) {
                pathRu = path + otherParams + "&lang=ru";
            } else {
                pathRu = path + "?lang=ru";
            }
        } else {
            pathRu = path + "?lang=ru";
        }
    } else if ($("#locale").text() === "ru") {
        if (window.location.search.length > 0) {
            otherParams = params.replace("&lang=ru", "");
            if (otherParams > 0) {
                pathEn = path + otherParams + "&lang=en";
            } else {
                pathEn = path + "?lang=en";
            }
        } else {
            pathEn = path + "?lang=en";
        }
    }

    $("#en").attr("href", pathEn);
    $("#ru").attr("href", pathRu);
})