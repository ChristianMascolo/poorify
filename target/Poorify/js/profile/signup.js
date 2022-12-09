
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