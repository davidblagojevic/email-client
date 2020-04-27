function highlightRow(row) {
  //ne reagujemo na klik na header tabele, samo obicne redove
  //this sadrzi red na koji se kliknulo
  if (!$(row).hasClass("header")) {
    //klasa highlighted postavlja pozadinu na plavu
    //njenim dodavanjem ili uklanjanjem oznacavamo da neki red
    //dobija, odnosno gubi selekciju
    //uklanjamo sa trenutno selektovanog
    $(".highlighted").removeClass("highlighted");
    //dodajemo na novi selektovani
    $(row).addClass("highlighted");
  }
}

function deleteEntity(entity, fetchFunction) {
  var id = getIdOfSelectedEntity();
  $.ajax({
    url: entity + "/" + id,
    type: "DELETE",
    headers: createAuthorizationTokenHeader(),
    success: function () {
      fetchFunction();
    }
  });
}

function getIdOfSelectedEntity() {
  var row = $(".highlighted");
  var id = row.find(".idCell").html();
  if (id == undefined) {
    console.log("No entity selected!");
    return null;
  }
  else {
    return id;
  }
}

function getNameOfSelectedEntity() {
  var row = $(".highlighted");
  var name = row.find(".nameCell").html();
  if (name == undefined) {
    console.log("No entity selected!");
    return null;
  }
  else {
    return name;
  }
}
/* EVENT HANDLERI */


