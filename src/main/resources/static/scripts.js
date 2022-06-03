function getRates() {
    $.ajax({
        url: 'http://localhost:8080/currency',
        method: 'GET',
        dataType: "application/json",
        complete: function (data) {
        let out = document.querySelector("#currency");
        if(data.status == 200) {
                    let content = JSON.parse(data.responseText);
                    let select = document.createElement("select");
                    for (var i = 0; i < content.length; i++) {
                        let option = document.createElement("option");
                        option.value = content[i];
                        option.text = content[i];
                        select.appendChild(option);
                    }
                    out.innerHTML = '';
                    out.insertAdjacentElement("afterbegin", select);
                } else {
                    var para = document.createElement("p");
                    para.innerHTML = 'Currency query wrong';
                    out.insertAdjacentElement("afterbegin", para);
                }
        }
    })
}

function loadResultGif() {
var code = $("#currency option:selected").val();
console.log(code);
    $.ajax({
        url: 'http://localhost:8080/get/' + code,
        method: 'GET',
        dataType: "application/json",
        complete: function (data) {
        let out = document.querySelector("#out");
        if(data.status == 200) {
            let content = JSON.parse(data.responseText);
            let img = document.createElement("img");
            img.src = content.data.id;
            out.innerHTML = '';
            out.insertAdjacentElement("afterbegin", img);
        } else {
            var para = document.createElement("p");
            para.innerHTML = 'Something went wrong. Try again :)';
            out.insertAdjacentElement("afterbegin", para);
        }
        }
    })
}
