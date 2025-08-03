import * as Api from "../api.js";

const submitButton = document.querySelector("#submitButton");
const emailInput = document.querySelector("#email");
const passwordInput = document.querySelector("#password");


function addAllEvents() {
    submitButton.addEventListener("click", handleSubmit);
    console.log("testest");
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

// function sendLoginRequest(data) {
//     return new Promise((resolve, reject) => {
//         $.ajax({
//             url: "/api/login",
//             method: "POST",
//             contentType: "application/json",
//             data: JSON.stringify(data),
//             xhrFields: { withCredentials: true },
//             success: function(response) {
//                 resolve(response);
//             },
//             error: function(jqXHR) {
//                 reject(new Error(`Error: ${jqXHR.status}`));
//             }
//         });
//     });
// }


