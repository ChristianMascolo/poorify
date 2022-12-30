
function checkFollowing(id, type) {
    $.post("CheckFollowing", {id: String(id), type: type}, function(data){
        let outcome = data.outcome[0];
        if(outcome) {
            let span = document.getElementById("following-span");
            span.innerHTML = "Following";
        }
    });
}

function follow(id, type) {
    $.post("CheckFollowing", {id: String(id), type: type}, function(data){
        let outcome = data.outcome[0];
        if(!outcome) {
            if(type == "artist") {
                $.post("FollowArtist", {id: String(id)}, function(data){
                    notify("Following artist");
                });
            }
            else {
                $.post("FollowUser", {id: String(id)}, function(data){
                    notify("Following user");
                });
            }
        }
        else {
            if(type == "artist") {
                $.post("UnfollowArtist", {id: String(id)}, function(data){
                    notify("Unfollowed artist");
                });
            }
            else {
                $.post("UnfollowUser", {id: String(id)}, function(data){
                    notify("Unfollowed user");
                });
            }

        }

        if(type == "artist")
            navToArtist(id, false);
        else
            navToUser(id, false);

    });
}