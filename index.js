class Problem {
    constructor(question, answer, type) {
        this.question = question;
        this.answer = answer;
        this.type = type;
    }
}

var problemList = [
    ["숫자를 0으로 나눌 때처럼 잘못된 산술 연산을 하는 경우", "ArithmeticException", "실행"],
    ["잘못된 값을 메소드로 전달하는 경우", "IllegalArgumentException", "실행"],
    ["배열 등에서 범위를 벗어난 인덱스 접근", "IndexOutOfBoundsException", "실행"],
    ["요청된 원소를 찾을 수 없는 경우", "NoSuchElementException", "실행"],
    ["가리키는 것이 없는 참조변수의 활용", "NullPointerException", "실행"],
    ["잘못된 형식의 숫자 변환", "NumberFormatException", "실행"],
    ["클래스를 찾을 수 없는 경우", "ClassNotFoundException", "일반"],
    ["인터럽트가 발생한 경우", "InterruptedException", "일반"],
    ["입출력 문제가 생긴 경우", "IOException", "일반"]
];
var problems = [];
var problemRemain = [];
var problemSpan = document.getElementById('question');
var answerInput = document.getElementById('answer');
var descSpan = document.getElementById('desc');
var console_text = document.getElementById('console_text');
var curIdx = -1;

function start() {
    for(var idx = 0 ; idx < problemList.length ; idx++) {
        problems.push(new Problem(
            problemList[idx][0],
            problemList[idx][1],
            problemList[idx][2] + " 예외"
        ));
    }
    
}

function set_question() {
    if(problemRemain.length == 0) {
        for(var idx = 0 ; idx < problemList.length ; idx++) {
            problemRemain.push(idx);
        }
    }
    var ranIdx = Math.floor(Math.random() * problemRemain.length);
    curObj = problems[problemRemain[ranIdx]];
    problemSpan.innerText = curObj.question;
    descSpan.innerText = curObj.type;
    problemRemain.splice(ranIdx, 1);
    answerInput.value = "";
    
}

function submit() {
    if(String(answerInput.value).toLowerCase() == curObj.answer.toLowerCase()) {
        console_text.style.backgroundColor = "green";
        console_text.innerText = "정답!";
        set_question();
    }
    else {
        console_text.style.backgroundColor = "red";
        console_text.innerText = "땡!\n정답 - " + curObj.answer;
    }
    
}

console_text.innerText = "";
console_text.style.backgroundColor = "rgba(0,0,0,0)";
start();
set_question();