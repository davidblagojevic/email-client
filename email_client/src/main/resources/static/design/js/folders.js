
var edit = false;
$(document).ready(function () {
    account = getAccount();

    fetchFolders(account);


    $(document).on("click", 'tr', function (event) {
        highlightRow(this);
    });

    //za brisanje
    $(document).on("click", '#delete', function (event) {
        var name = getNameOfSelectedEntity();
        if (name != null) {
            $('#deletePromptText').text("Jeste li sigurni da želite da izbiršete kontakt: " + name);
            $('#deletePromptModal').modal('show');
        }

    });

    $(document).on("click", '.deletePromptClose', function (event) {
        $('#deletePromptModal').modal('hide');
    });

    $(document).on("click", '#doDeleteFolder', function (event) {
        deleteEntity('folders', fetchFolders);
        $('#deletePromptModal').modal('hide');
    });

    //za dodavanje
    $(document).on("click", '#add', function (event) {
        edit = false;
        $('#nameInput').val('');
        $('#addModalScrollable').modal('show');
    });

    $(document).on('click', '.addModalClose', function (event) {
        $('#addModalScrollable').modal('hide');
    });

    $(document).on('click', '#doAddFolder', function (event) {

        //za sacuvaj
        if (edit) {
            editFolder();
        }else{
            addFolder();

        }
        $('#nameInput').val('');
        $('#addModalScrollable').modal('hide');

    });

    //za editovanje
    $(document).on('click', '#edit', function (event) {
        var id = getIdOfSelectedEntity();
        if (id == null) {
            return;
        }
        edit = true;
        prepareEditFolder();
        $('#addModalScrollable').modal('show');
    });
});


function fetchFolders() {
    var account = getAccount();
    $.ajax({
        type: "GET",
        url: "folders?account=" + account,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader()
    }).then(
        function (data) {
            $("#dataTableBody").empty();
            for (i = 0; i < data.length; i++) {
                newRow = "<tr><td class=\"nameCell\">" + data[i].name + "</td>"
                    + "<td class=\"conditionCell\">" + data[i].rule.condition + "</td>"
                    + "<td class=\"operationCell\">" + data[i].rule.operation + "</td>"
                    + "<td class=\"idCell\">" + data[i].id + "</td>" + "</tr>"
                $("#dataTableBody").append(newRow);
            }
        });

}
function addFolder() {
    var account = getAccount();
    var folder = {
        name: $('#nameInput').val(),
        folders: [],
        rule: {
            condition: $("input[name='conditionRadio']:checked").val(),
            operation: $("input[name = 'operationRadio']:checked").val()
        },
        messages: []
    }
    $.ajax({
        type: "POST",
        url: "folders?account=" + account,
        data: JSON.stringify(folder),
        dataType: "json",
        contentType: "application/json",
        headers: createAuthorizationTokenHeader(),
        success: function (data) {
            fetchFolders();
            $("nameInput").val('');
        }
    });
}
function prepareEditFolder() {
    var row = $(".highlighted");

    var editedFolder = {
        id: row.find(".idCell").html(),
        name: row.find(".nameCell").html(),
        rule: {
            condition: row.find(".conditionCell").html(),
            operation: row.find(".operationCell").html()
        }
    }
    $("#nameInput").val(editedFolder.name);
    switch (editedFolder.rule.condition) {
        case "TO":
            $("#radioTo").prop("checked", true);
            break;

        case "FROM":
            $("#radioFrom").prop("checked", true);
            break;
        case "CC":
            $("#radioCC").prop("checked", true);
            break;
        case "SUBJECT":
            $("#radioSubject").prop("checked", true);
            break;
    }
    switch (editedFolder.rule.operation) {
        case "MOVE":
            $("#radioMove").prop("checked", true);
            break;
        case "COPY":
            $("#radioCopy").prop("checked", true);
            break;
        case "DELETE":
            $("#radioDelete").prop("checked", true);
            break;

    }


}

function editFolder() {
    var editedFolder = {
        id: getIdOfSelectedEntity(),
        name: $('#nameInput').val(),
        rule: {
            condition: $("input[name='conditionRadio']:checked").val(),
            operation: $("input[name = 'operationRadio']:checked").val()
        }
    };

    var account = getAccount();
    $.ajax({
        type: "PUT",
        url: "folders?account=" + account,
        data: JSON.stringify(editedFolder),
        dataType: "json",
        contentType: "application/json",
        headers: createAuthorizationTokenHeader(),
        success: function (data) {
            fetchFolders();
            $("nameInput").val('');
            edit = false;
        }
    });
    edit = false;





}

// function addContact(){

//     var contact = {
//         first: $('#firstInput').val(),
//         last: $('#lastInput').val(),
//         display: $('#displayInput'),
//         email: $('#emailInput'),
//         radioValue = $("input[name='formatRadio']:checked").val(),
//         photo:{
//             //////STAOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
//         }
//     }
// }