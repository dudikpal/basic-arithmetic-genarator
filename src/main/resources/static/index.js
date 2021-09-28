

window.onload = function () {

    loadMainPage();

}

let generateExamplesButton = document.querySelector("#example-genarator-button");

let loadExamplesButton = document.querySelector("#goto-example-page-button");

let taskCounter = 0;


let url = '/api/examples';

let injectedField = document.querySelector("#page-body");

function generateExamples() {

    loadExamplesButton = document.querySelector("#goto-example-page-button");
    loadExamplesButton.disabled = false;

    let examplePcs = document.querySelector("#examplePcsInput");
    let operandPcs = document.querySelector("#operand-pcs");
    let operandRangeMin = document.querySelector("#operand-minvalue");
    let operandRangeMax = document.querySelector("#operand-maxvalue");
    let operator = document.querySelector('[name="operatorType"]:checked');
    let unknownField = document.querySelector('[name="unknownItem"]:checked');





        if (operandPcs.value > 3) {
            operandPcs.value = 3;
        } else if (operandPcs.value < 2) {
            operandPcs.value = 2;
        }

        if (parseInt(operandRangeMin.value) > parseInt(operandRangeMax.value)) {
            let temp = operandRangeMin.value;
            operandRangeMin.value = operandRangeMax.value;
            operandRangeMax.value = temp;
        }

        if (operandRangeMin.value < -1000) {
            operandRangeMin.value = -1000;
        } else if (operandRangeMin.value > 900) {
            operandRangeMin.value = 900;
        }

        if (operandRangeMax.value > 1000) {
            operandRangeMax.value = 1000;
        } else if (operandRangeMax.value < -900) {
            operandRangeMax.value = -900;
        }

        if (examplePcs.value < 1) {
            examplePcs.value = 1;
        } else if (examplePcs.value > 100) {
            examplePcs.value = 100;
        }



    let data = JSON.stringify(
        {
            "examplePcs": examplePcs.value,
            "operandPcs": operandPcs.value,
            "operandRangeMin": operandRangeMin.value,
            "operandRangeMax": operandRangeMax.value,
            "operator": operator.value,
            "unknownField": unknownField.value
        }
    );


    taskCounter += parseInt(examplePcs.value);
    let taskCounterSpan = document.querySelector("#taskCount");
    taskCounterSpan.innerHTML = taskCounter + " feladat vár megoldásra";

    fetch(url, {
        method: "POST",
        body: data,
        headers: {
            "Content-Type": "application/json"
        }
    });

    loadExamplesButton.disabled = false;

}



function beginSolution() {

    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {

            injectedField.innerHTML = jsonData.example;
        })
        .then(function() {

            fetch("templates/examples-bottom.txt")
                .then(response => response.text())
                .then((data) => {
                    injectedField.innerHTML += data;
                });
            fetch("templates/check-bottom.txt")
                .then(response => response.text())
                .then((data) => {
                    injectedField.innerHTML += data;
                });
        });


}


function checkSolutions() {

    let solutionsButton = document.querySelector("#solutions-button");
    let gotoStartButton = document.querySelector("#go-to-index-button");
    let exampleDiv = document.querySelectorAll(".example");

    for (let i = 0; i < exampleDiv.length; i++) {

        let resultSpan = exampleDiv[i].querySelector(".resultSpan");
        let inputField = exampleDiv[i].querySelector("input");
        let expected = resultSpan.innerHTML.split("Megoldás:\t")[1];
        let actual = exampleDiv[i].querySelector("input").value;

        if (expected != actual) {
            inputField.style.borderWidth = "3px";
            inputField.style.borderColor = "crimson";
            resultSpan.style.color = "red";

        }

        resultSpan.hidden = false;
        inputField.readOnly = true;

    }

    solutionsButton.hidden = true;
    gotoStartButton.hidden = false;



}


function goToIndex() {

    fetch(url, {
        method: "DELETE"
    });
    loadMainPage();

}


function loadMainPage() {

    fetch("templates/index-content.txt")
        .then(response => response.text())
        .then((data) => {
            injectedField.innerHTML = data;
        });

    taskCounter = 0;

}



