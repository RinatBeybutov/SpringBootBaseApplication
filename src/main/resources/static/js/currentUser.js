function loadUser() {
    var userId = localStorage.getItem('id');
    console.log(userId);

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
                var user = JSON.parse(this.responseText);
                html = user.name + "<br>"
                + user.surname + "<br>"
                + user.role + "<br>"
                + user.project;
                document.getElementById("userInfo").innerHTML = html;
        }
    };
    xhttp.open("GET", "http://localhost:8080/users/"+userId, true);
    xhttp.send();

}

loadUser();