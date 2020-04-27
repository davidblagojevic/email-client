$(document).ready(function () {


    //tabela klik
    $(document).on("click", 'tr', function (event) {
        highlightRow(this);
    });
    //za brisanje
	$(document).on("click", '#delete', function(event){
		var name = getNameOfSelectedEntity();
		if(name!=null){
			$('#deletePromptText').text("Jeste li sigurni da želite da izbiršete kontakt: " + name);
			$('#deletePromptModal').modal('show');
		}

    });


    $(document).on("click", '.deletePromptClose', function(event){
		$('#deletePromptModal').modal('hide');
	});
    
    $(document).on("click", '#doDeleteContact', function(event){
		deleteEntity('contacts', fetchContacts);
		$('#deletePromptModal').modal('hide');
	});
    //za dodavanje
	$(document).on("click", '#add', function(event){
		$('#addModalScrollable').modal('show');
	});

    fetchContacts();
});


function fetchContacts() {
    $.ajax({
        type: "GET",
        url: "contacts",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader()
    }).then(
        function (data) {
            $("#dataTableBody").empty();
            for (i = 0; i < data.length; i++) {
                newRow = "<tr><td class=\"photoCell\">" + 'slika' + "</td>"
                    + "<td class=\"nameCell\">" + data[i].display + "</td>"
                    + "<td class=\"emailCell\">" + data[i].email + "</td>"
                    + "<td class=\"idCell\">" + data[i].id + "</td>" + "</tr>"
                $("#dataTableBody").append(newRow);
            }
        });

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