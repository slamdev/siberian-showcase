"use strict";

$(document).ready(function () {

    var endpointUrl = "rest/hl7";

    $("#uploadFile").click(function () {
        var data = new FormData();
        data.append("inputFile", $("#inputFile")[0].files[0]);
        $.ajax({
            url: endpointUrl,
            data: data,
            contentType: false,
            processData: false,
            type: "POST"
        }).done(renderSuccessUpload).fail(renderError);
    });

    $("#showFile").click(function () {
        var fileName = $("#fileName").val();
        $.ajax(endpointUrl + "/" + fileName).done(renderFileContent).fail(renderError);
    });

    function renderFileContent(result) {
        $("#fileContent").text(result);
        $("#fileContentPanel").show();
    }

    function renderSuccessUpload(result) {
        renderFileContent("File uploaded.")
    }

    function renderError(result) {
        console.error("Ajax error occurred", result);
        renderFileContent("Error occurred. See console logs for details.")
    }
});