import * as Api from "../api.js";

const submitButton = document.querySelector("#submitButton");
const emailInput = document.querySelector("#email");
const passwordInput = document.querySelector("#password");


function addAllEvents() {
    submitButton.addEventListener("click", handleSubmit);
}

addAllEvents();

async function handleSubmit(e) {
    e.preventDefault();

    const email = emailInput.value;
    const password = passwordInput.value;
    const data = {email, password};
    const result = await Api.post("/api/login", data);

    try {
        console.log("응답 데이터:", result);
        alert("로그인 성공");
    } catch (error) {
        console.error(error);
        alert("로그인 실패");
    }
}



