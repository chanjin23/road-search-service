import * as Api from "../api.js";
import {checkLogin} from "../useful-function.js";

const updateForm = document.getElementById("updateForm");
const errorMsg = document.getElementById("error-msg");
const historyBtn = document.getElementById("historyBtn");
const infoBtn = document.getElementById("infoBtn");
const resultList = document.getElementById("resultList");
const updateInfoSection = document.getElementById("updateInfoSection");
let name = document.getElementById("name");
let email = document.getElementById("email");
let fullData = [];

function addAllEvents() {
    historyBtn.addEventListener("click", searchHistoryHandleSubmit);
    infoBtn.addEventListener("click", infoBtnHandleSubmit);
    updateForm.addEventListener("submit", submitHandleSubmit);
}

// checkLogin();
addAllEvents();

async function searchHistoryHandleSubmit(e) {
    e.preventDefault();
    const url = '/api/searchHistory';

    updateInfoSection.classList.add('d-none');
    resultList.classList.remove('d-none');

    const data = await Api.get(url);
    fullData = Array.isArray(data) ? data : [];
    renderPage();
}

async function infoBtnHandleSubmit(e) {
    e.preventDefault();

    const userUrl = '/api/user';

    resultList.classList.add('d-none');
    updateInfoSection.classList.remove('d-none');

    const data = await Api.get(userUrl);

    name.value = data.name;
    email.value = data.email;
}

async function submitHandleSubmit(e) {
    e.preventDefault();

    const currentPassword = document.getElementById("currentPassword").value;
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    let data;
    const url = '/api/user';

    if (newPassword !== confirmPassword) {
        errorMsg.textContent = "비밀번호가 일치하지 않습니다.";
        return;
    }

    data = {currentPassword, newPassword};

    try {
        const result = await Api.put(url, data);
        alert("수정완료");
        window.location.href = "/";
    } catch (err) {
        console.log(err);
    }
}

function renderPage() {
    resultList.innerHTML = "";

    if (fullData.length === 0) {
        resultList.innerHTML = "<li>검색한 기록이 없습니다. </li>";
        return;
    }

    fullData.forEach((item) => {
        const li = document.createElement("li");
        li.innerHTML = `
            <div>${item.roadAddress}</div>
            <div>
                ${item.buildName ? `[건물명] ${item.buildName}` : ""} 
                ${item.zipcode ? `(우편번호: ${item.zipcode})` : ""}
            </div>
        `;
        li.classList.add("list-group-item");

        resultList.appendChild(li);
    });
}