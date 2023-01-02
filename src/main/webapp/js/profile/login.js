

function login() {

    let email = document.getElementById("email").children[1].value;
    //let password = btoa(document.getElementById("password").children[1].value); //Base64
    let password = document.getElementById("password").children[1].value;

    $.post("CheckCredentials", {email: email, password: password}, function(data){
        let outcome = data.outcome[0];
        if(outcome) {
            document.getElementById("login-form").submit();
        }
        else {
            let error = document.getElementById("error-message");
            error.innerHTML = "Email or password entered are incorrect!";
        }
    });

}

function logout() {
    $.post("Logout", {}, function(data){

    });
}