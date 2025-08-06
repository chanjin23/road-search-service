import * as Api from "../api.js";

export const checkLogin = async () => {
    const isValid = await validateToken();
    console.log("로그인 유효성 검증");

    if (!isValid) {
        const isValidRefresh = await refresh();

        // 리프레시토큰을 정상적으로 발급받았다면 재검증
        if (isValidRefresh) {
            console.log("리프레시토큰 재발급완료!");
            return await checkLogin();
        }

        // 현재 페이지의 url 주소 추출하기
        const pathname = window.location.pathname;
        const search = window.location.search;

        // 로그인 후 다시 지금 페이지로 자동으로 돌아가도록 하기 위한 준비작업임.
        alert("회원만 이용가능한페이지입니다.");
        window.location.replace(`/login`);
        return false;

    }
    return true;
}

export const blockIfLogin = async () => {
    const isValid = await validateToken();

    if (isValid) {
        alert("로그인 상태에서는 접근할 수 없는 페이지입니다.");
        window.location.replace("/");
    }
};

async function validateToken() {
    const endpoint = "/api/protected";
    try {
        console.log(endpoint);
        const data = await Api.get(endpoint);

        return true;   // data가 정상적으로 왔다면 로그인된 상태로 간주
    } catch (err) {
        return false;
    }
}

async function refresh() {
    const endpoint = "/api/token/refresh";
    try {
        console.log(endpoint);
        const data = await Api.get(endpoint);

        return true;   // data가 정상적으로 왔다면 로그인된 상태로 간주
    } catch (err) {
        return false;
    }
}