const operandPcs = document.querySelector("#operand-pcs");
const examplePcs = document.querySelector("#examplePcsInput");
const operandRangeMin = document.querySelector("#operand-minvalue");
const operandRangeMax = document.querySelector("#operand-maxvalue");
const operator = document.querySelector('[name="operatorType"]:checked');
const unknownField = document.querySelector('[name="unknownItem"]:checked');

const operandPcsMin = operandPcs.getAttribute("min");
const operandPcsMax = operandPcs.getAttribute("max");


function validateParameters(paramName, paramValue) {
    console.log(operandPcsMin);
    console.log(operandPcsMax);
}