
checkNations();

function checkNations() {
    if($('select').length) {
        $('select').empty();
        $.post("CheckNations", {}, function(data){
            for(let i = 0; i < data.iso.length; i++) {
                let option = document.createElement("option");
                option.value = data.iso[i];
                option.innerHTML = data.name[i];
                $('select').append(option);
            }
        });
    }
}

function signup(type) {
    let response = true;
    response = response && validateEmail(document.getElementById("email").children[0]);
    response = response && validatePassword(document.getElementById("password").children[0]);
    response = response && validateAlias(document.getElementById("alias").children[0]);
    response = response && validatePicture(document.getElementById("picture").children[0]);
    if (type == 'user')
        response = response && validateBirthdate(document.getElementById("birthdate").children[0]);
    return response;
}

function validateEmail(field) {
    let div = field.parentElement;
    div.children[1].innerHTML = "";

    let email = field.value.trim();
    let regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(email.match(regex)) {
        return true;
    }
    else {
        div.children[1].innerHTML = "Invalid email format!";
        return false;
    }
}

function validatePassword(field) {
    let div = field.parentElement;
    div.children[1].innerHTML = "";

    let password = field.value.trim();
    let regex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;
    if(password.match(regex)) {
        return true;
    }
    else {
        div.children[1].innerHTML = "Invalid password format!";
        return false;
    }
}

function validateAlias(field) {
    let div = field.parentElement;
    div.children[1].innerHTML = "";

    let alias = field.value.trim();
    if(alias.length >= 10) {
        return true;
    }
    else {
        div.children[1].innerHTML = "Alias should be at least 10 characters long!";
        return false;
    }
}

function validatePicture(field) {
    let div = field.parentElement;
    div.children[2].innerHTML = "";

    let file = field.files[0];
    let img = new Image();

    div.children[1].children[0].innerHTML = field.value.replace(/.*[\/\\]/, '');

    let objurl = window.URL.createObjectURL(file);
    img.onload = function() {
        if(this.width >= 500 && this.height >= 500) {
            return true;
        } else {
            div.children[2].innerHTML = "Picture should be at least 500x500!";
            return false;
        }
    };
    img.src = objurl;
}

function validateBirthdate(field) {
    let div = field.parentElement;
    div.children[1].innerHTML = "";

    let birthdate = field.value.trim();
    if(birthdate != "") {
        return true;
    }
    else {
        div.children[1].innerHTML = "Enter your birthdate!";
        return false;
    }
}
