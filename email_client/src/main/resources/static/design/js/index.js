$(document).ready(function () {
    var account = getAccount();


    fetchEmails(account);

    $(document).on("click", 'tr', function (event) {
        highlightRow(this);
    });
  
    //za slanje
    $(document).on("click", '#add', function (event) {
        $('#addModalScrollable').modal('show');
    });

    //pretraga
    $(document).on("click", '#search', function (event) {  
        $('#collapseSearch').collapse('toggle');
    });

    $(document).on("click", '#doReset', function (event) {
       $("#collapseSearch").collapse('hide'); 
    });

});

function fetchEmails(accountParam) {
    
    $.ajax({
        type: "GET",
        url: "messages/account/" + accountParam,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader()
    }).then(
        function (data) {
            $("#dataTableBody").empty();
            for (i = 0; i < data.length; i++) {
                newRow = "<tr><td class=\"fromCell\">" + data[i].from + "</td>"
                    + "<td class=\"toCell\">" + data[i].to + "</td>"
                    + "<td class=\"subjectCell\">" + data[i].subject + "</td>"
                    + "<td class=\"contentCell\">" + data[i].content.substring(0, 8) + "..." + "</td>"
                    + "<td class=\"dateTimeCell\">" + data[i].dateTime + "</td>"
                    + "<td class=\"idCell\">" + data[i].id + "</td>" + "</tr>"
                $("#dataTableBody").append(newRow);
            }
        });

}