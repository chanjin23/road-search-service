import * as Api from "../api.js";

const searchButton = document.getElementById("searchBtn");

function addAllEvents() {
    searchButton.addEventListener("click", handleSubmit);
}

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
        // ⭐ 실제 사용 시 해당 URL을 도로명주소 검색 API 주소로 변경하세요
        const url = `/api/search`;
        const params = {roadAddress: keyword};

        const data = await Api.get(url, params);

        if (!Array.isArray(data) || data.length === 0) {
            renderResult([]);
        } else {
            renderResult(data);
        }
    } catch (err) {
        console.error("API 호출 중 오류:", err);
        alert("검색 중 오류가 발생했습니다.");
    }
}

document.getElementById("mypageBtn").addEventListener("click", () => {
    // TODO: 마이페이지 이동 처리
    alert("마이페이지로 이동합니다.");
});

document.getElementById("logoutBtn").addEventListener("click", () => {
    // TODO: 로그아웃 처리
    alert("로그아웃되었습니다.");
});

function renderResult(list) {
    const resultList = document.getElementById("resultList");
    resultList.innerHTML = "";

    if (list.length === 0) {
        resultList.innerHTML = "<li>검색 결과가 없습니다.</li>";
        return;
    }

    list.forEach((item) => {
        const li = document.createElement("li");

        // HTML 구조 구성 (도로명주소 / 건물명 / 우편번호)
        li.innerHTML = `
            <div>${item.roadAddress}</div>
            <div>${item.buildName ? `[건물명] ${item.buildName}` : ""} ${item.zipcode ? `(우편번호: ${item.zipcode})` : ""}
            </div>`;

        li.style.cursor = "pointer";
        li.addEventListener("click", () => {
            // URL 인코딩 필수
            const url = new URL('/detail.html', window.location.origin);
            // url.searchParams.set('address', item.roadAddress);
            url.searchParams.set('xpos', item.xpos);
            url.searchParams.set('ypos', item.ypos);

            window.location.href = url.toString();
        });

        resultList.appendChild(li);
    });
}