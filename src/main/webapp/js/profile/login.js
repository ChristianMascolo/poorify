

function login() {

    //VALIDAZIONE CAMPI

    let email = document.getElementById("email").children[1].value;
    //let password = btoa(document.getElementById("password").children[1].value); //Base64
    let password = document.getElementById("password").children[1].value;

    $.post("CheckCredentials", {email: email, password: password}, function(data){
        console.log(data.outcome[0]);
    });

}