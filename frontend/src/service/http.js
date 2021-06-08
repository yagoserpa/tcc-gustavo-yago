


function request(params) {
  if (!validarAccessToken()) {
    if (!conseguirOutro()) {
      throw new Error("Não ta logado");
    }
  }
  var myHeaders = new Headers();
  myHeaders.append("Authorization", accesstoken);
  var myInit = { method: 'GET', headers: myHeaders, };
  fetch(params.url, myInit)
    .then(r => r.json())
}