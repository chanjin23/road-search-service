import * as Api from "../api.js";

const registerForm = document.getElementById("register-form");
const nameInput = document.getElementById("name");
const emailInput = document.getElementById("email");
const passwordInput = document.getElementById("password");
const confirmPasswordInput = document.getElementById("confirmPassword");
const errorMsg = document.getElementById("error-msg");

// 실행
addAllEvents();

function addAllEvents() {
    registerForm.addEventListener("submit", handleSubmit);
}

async function handleSubmit(e) {
    e.preventDefault();

    // 입력값 수집
    const name = nameInput.value.trim();
    const email = emailInput.value.trim();
    const password = passwordInput.value.trim();
    const confirmPassword = confirmPasswordInput.value.trim();

    // 이름 검사
    if (!validateName(name)) {
        errorMsg.textContent = "이름을 입력해 주세요.";
        return;
    }

    // 이메일 검사
    if (!validateEmail(email)) {
        errorMsg.textContent = "유효한 이메일 형식을 입력해 주세요.";
        return;
    }

    // 비밀번호와 비밀번호 확인 일치 검사
    if (password !== confirmPassword) {
        errorMsg.textContent = "비밀번호가 일치하지 않습니다.";
        return;
    }

    // 비밀번호 형식 검사
    if (!validatePassword(password)) {
        errorMsg.textContent = "비밀번호는 8자 이상 20자 이하여야 하며, 알파벳, 숫자, 특수문자를 포함해야 합니다.";
        return;
    }

    const data = {email, password, name};

    try {
        const result = await Api.post("/api/signup", data);

        console.log("응답 데이터:", result);
        alert("회원가입이 완료되었습니다.");
        window.location.href = "/login";

    } catch (err) {
        console.error(err);
        console.log("회원가입실패");
    }
}

function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function validatePassword(password) {
    // 8~20자, 알파벳 최소 1, 숫자 최소 1, 특수문자 최소 1 포함
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&#])[A-Za-z\d@$!%*?&#]{8,20}$/;
    return passwordRegex.test(password);
}

function validateName(name) {
    return name.trim().length > 0;
}