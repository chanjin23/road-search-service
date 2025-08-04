import * as Api from "../api.js";
import{
    blockIfLogin
} from "../useful-function.js";

const submitButton = document.querySelector("#submitButton");
const emailInput = document.querySelector("#email");
const passwordInput = document.querySelector("#password");


function addAllEvents() {
    submitButton.addEventListener("click", handleSubmit);
}

blockIfLogin();
addAllEvents();

async function handleSubmit(e) {
    e.preventDefault();

    const email = emailInput.value;
    const password = passwordInput.value;
    const data = {email, password};
    try {
        const result = await Api.post("/api/login", data);

        console.log("응답 데이터:", result);
        alert("로그인 성공");
        window.location.href = "/home/home.html";
    } catch (error) {
        console.error(error);
        alert("로그인 실패");
        window.location.href = "/login/login.html";
    }
}



