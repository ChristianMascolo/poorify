
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


    if (type == 'user') {

    } else {

    }
}

function validateEmail(field) {
    let div = field.parentElement;
    div.children[2].innerHTML = "";

    let email = field.value.trim();
    let regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(email.match(regex)) {
        return true;
    }
    else {
        div.children[2].innerHTML = "Invalid email format!";
        return false;
    }
}

function validatePassword(field) {
    let div = field.parentElement;
    div.children[2].innerHTML = "";

    let password = field.value.trim();
    let regex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;
    if(password.match(regex)) {
        return true;
    }
    else {
        div.children[2].innerHTML = "Invalid password format!";
        return false;
    }
}

function validateAlias(field) {
    let div = field.parentElement;
    div.children[2].innerHTML = "";

    let alias = field.value.trim();
    if(alias.length >= 10) {
        return true;
    }
    else {
        div.children[2].innerHTML = "Alias should be at least 10 characters long!";
        return false;
    }
}

function validatePicture(field) {
    let div = field.parentElement;
    div.children[3].innerHTML = "";

    let file = field.files[0];
    let img = new Image();

    let objurl = window.URL.createObjectURL(file);
    img.onload = function() {
        if(this.width >= 500 && this.height >= 500) {
            return true;
        } else {
            div.children[3].innerHTML = "Picture should be at least 500x500!";
            return false;
        }
    };
    img.src = objurl;
}

function validateBirthdate(field) {
    let div = field.parentElement;
    div.children[2].innerHTML = "";

    let birthdate = field.value.trim();
    if(birthdate != "") {
        return true;
    }
    else {
        div.children[2].innerHTML = "Enter your birthdate!";
        return false;
    }
}