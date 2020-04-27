$(document).ready(function () {
    fetchEmailAccounts();

    //tabela klik
    $(document).on("click", 'tr', function (event) {
        highlightRow(this);
    });

    //za brisanje
    $(document).on("click", '#delete', function (event) {
        var name = getNameOfSelectedEntity();
        if (name != null) {
            $('#deletePromptText').text("Jeste li sigurni da želite da izbiršete email nalog: " + name);
            $('#deletePromptModal').modal('show');
        }

    });


    $(document).on("click", '.deletePromptClose', function (event) {
        $('#deletePromptModal').modal('hide');
    });

    $(document).on("click", '#doDeleteEmailAccount', function (event) {
        deleteEntity('accounts', fetchEmailAccounts());
        $('#deletePromptModal').modal('hide');
    });

    //za dodavanje
    $(document).on("click", '#add', function (event) {
        $('#addModalScrollable').modal('show');
    });
    //za redirekciju ka pojedinacnom email nalog
    $(document).on('click', '#choose', function (event) {
        var email = getNameOfSelectedEntity();
        if (email !== null) {
            window.location.replace('index.html?account=' + email );            
        }


    })
});


function fetchEmailAccounts() {
    $.ajax({
        type: "GET",
        url: "accounts",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader()
    }).then(
        function (data) {
            $("#dataTableBody").empty();
            for (i = 0; i < data.length; i++) {
                newRow = "<tr><td class=\"nameCell\">" + data[i].username + "</td>"
                    + "<td class=\"pop3ImapCell\">" + data[i].pop3Imap + "</td>"
                    + "<td class=\"smtpCell\">" + data[i].smtp + "</td>"
                    + "<td class=\"idCell\">" + data[i].id + "</td>" + "</tr>"
                $("#dataTableBody").append(newRow);
            }
        });

}