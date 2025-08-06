import * as Api from "../api.js";
import {checkLogin} from "../useful-function.js";

const updateForm = document.getElementById("updateForm");
const errorMsg = document.getElementById("error-msg");
const historyBtn = document.getElementById("historyBtn");
const infoBtn = document.getElementById("infoBtn");
const resultList = document.getElementById("resultList");
const updateInfoSection = document.getElementById("updateInfoSection");
const prevBtn = document.getElementById("prevBtn");
const nextBtn = document.getElementById("nextBtn");
const pageInfo = document.getElementById("pageInfo");
let name = document.getElementById("name");
let email = document.getElementById("email");
let fullData = [];
let currentPage = 1;
const pageSize = 10;

function addAllEvents() {
    document.addEventListener("DOMContentLoaded", () => {
        loadUserInfo();
    })
    historyBtn.addEventListener("click", searchHistoryHandleSubmit);
    infoBtn.addEventListener("click", infoBtnHandleSubmit);
    updateForm.addEventListener("submit", submitHandleSubmit);
    prevBtn.addEventListener("click",prevBtnHandleSubmit);
    nextBtn.addEventListener("click",nextBtnHandleSubmit);
}

checkLogin();
addAllEvents();

async function searchHistoryHandleSubmit(e) {
    e.preventDefault();
    const url = '/api/searchHistory';
    const parameter = {
        page: currentPage
    }

    updateInfoSection.classList.add('d-none');
    resultList.classList.remove('d-none');
    prevBtn.classList.remove('d-none');
    pageInfo.classList.remove('d-none');
    nextBtn.classList.remove('d-none');

    const data = await Api.get(url, parameter);
    fullData = Array.isArray(data) ? data : [];
    renderPage();

    const totalCount = await Api.get('/api/searchHistory/count');
    updatePageInfoByCount(totalCount);
}

async function infoBtnHandleSubmit() {
    resultList.classList.add('d-none');
    prevBtn.classList.add('d-none');
    pageInfo.classList.add('d-none');
    nextBtn.classList.add('d-none');

    updateInfoSection.classList.remove('d-none');
}

async function loadUserInfo() {
    const userUrl = '/api/user';

    resultList.classList.add('d-none');
    prevBtn.classList.add('d-none');
    pageInfo.classList.add('d-none');
    nextBtn.classList.add('d-none');
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

    if (!validatePassword(password)) {
        errorMsg.textContent = "비밀번호는 8자 이상 20자 이하여야 하며, 알파벳, 숫자, 특수문자를 포함해야 합니다.";
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
            <div class="text-muted small">
                ${item.createdAt ? item.createdAt : ""}
            </div>
        `;
        li.classList.add("list-group-item");

        resultList.appendChild(li);
    });
}

function updatePageInfoByCount(totalCount) {
    const totalPages = Math.ceil(totalCount / pageSize);
    pageInfo.textContent = `${currentPage} / ${totalPages}`;

    prevBtn.disabled = (currentPage === 1);
    nextBtn.disabled = (currentPage === totalPages);
}

function prevBtnHandleSubmit() {
    if (currentPage > 1) {
        currentPage--;
        searchHistoryHandleSubmit(new Event("click"));
    }
}

function nextBtnHandleSubmit() {
    currentPage++;
    searchHistoryHandleSubmit(new Event("click"));
}

function validatePassword(password) {
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&#])[A-Za-z\d@$!%*?&#]{8,20}$/;
    return passwordRegex.test(password);
}