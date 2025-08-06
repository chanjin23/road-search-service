import * as Api from "../api.js";
import {checkLogin} from "../useful-function.js";

const searchButton = document.getElementById("searchBtn");
const resultList = document.getElementById("resultList");
const prevBtn = document.getElementById("prevBtn");
const nextBtn = document.getElementById("nextBtn");
const pageInfo = document.getElementById("pageInfo");

let fullData = [];      // 전체 검색 결과 저장
let currentPage = 1;    // 현재 페이지
const pageSize = 10;     // 한 페이지에 몇 개 보여줄지

function addAllEvents() {
    searchButton.addEventListener("click", handleSubmit);
    prevBtn.addEventListener("click", goPrevPage);
    nextBtn.addEventListener("click", goNextPage);
}

checkLogin();
addAllEvents();

async function handleSubmit(e) {
    e.preventDefault();

    const keyword = document.getElementById("searchInput").value.trim();
    if (!keyword) {
        alert("검색어를 입력해주세요.");
        return;
    }
    console.log("검색 요청:", keyword);

    try {
        const url = `/api/search`;
        const params = {roadAddress: keyword};

        const data = await Api.get(url, params);
        fullData = Array.isArray(data) ? data : [];
        currentPage = 1;
        renderPage();
    } catch (err) {
        checkLogin();
    }
}

function renderPage() {
    resultList.innerHTML = "";

    if (fullData.length === 0) {
        resultList.innerHTML = "<li>검색 결과가 없습니다.</li>";
        pageInfo.textContent = "0 / 0";
        prevBtn.disabled = true;
        nextBtn.disabled = true;
        return;
    }

    const startIndex = (currentPage - 1) * pageSize;
    const endIndex = Math.min(startIndex + pageSize, fullData.length);
    const pageItems = fullData.slice(startIndex, endIndex);

    pageItems.forEach((item) => {
        const li = document.createElement("li");
        li.innerHTML = `
            <div>${item.roadAddress}</div>
            <div>
                ${item.buildName ? `[건물명] ${item.buildName}` : ""} 
                ${item.zipcode ? `(우편번호: ${item.zipcode})` : ""}
            </div>
        `;

        li.style.cursor = "pointer";
        li.addEventListener("click", async () => {
            const searchHistoryUrl = '/api/searchHistory';

            await Api.post(searchHistoryUrl, {
                roadAddress: item.roadAddress,
                zipcode: item.zipcode,
                buildName: item.buildName,
                xPos: item.xPos,
                yPos: item.yPos
            });

            const url = new URL('/map', window.location.origin);
            url.searchParams.set('xpos', item.xpos);
            url.searchParams.set('ypos', item.ypos);
            window.location.href = url.toString();
        });

        resultList.appendChild(li);
    });

    const totalPages = Math.ceil(fullData.length / pageSize);
    pageInfo.textContent = `${currentPage} / ${totalPages}`;

    prevBtn.disabled = currentPage === 1;
    nextBtn.disabled = currentPage === totalPages;
}

function goPrevPage() {
    if (currentPage > 1) {
        currentPage--;
        renderPage();
    }
}

function goNextPage() {
    const totalPages = Math.ceil(fullData.length / pageSize);
    if (currentPage < totalPages) {
        currentPage++;
        renderPage();
    }
}

document.getElementById("mypageBtn").addEventListener("click", () => {
    window.location.href = "/mypage";
});

document.getElementById("logoutBtn").addEventListener("click", async () => {
    await Api.post("/api/logout");
    window.location.href = "/login";
});
