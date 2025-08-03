async function get(endpoint, params = "") {
    const apiUrl = params ? `${endpoint}/${params}` : endpoint;
    console.log(`%cGET 요청: ${apiUrl} `, "color: #a25cd1;");

    const res = await fetch(apiUrl, {
        credentials: "include", // 쿠키 포함
    });

    if (!res.ok) {
        try {
            const errorContent = await res.json();
            const { reason } = errorContent;
            throw new Error(reason);
        } catch {
            throw new Error("서버 오류가 발생했습니다. 관리자에게 문의하세요.");
        }
    }

    const result = await res.json();
    return result;
}

async function post(endpoint, data) {
    const apiUrl = endpoint;
    const bodyData = JSON.stringify(data);

    const headers = {
        "Content-Type": "application/json",
    };

    const res = await fetch(apiUrl, {
        method: "POST",
        headers,
        body: bodyData,
        credentials: "include",
    });

    if (!res.ok && !res.created) {
        const errorContent = await res.json();
        const { reason } = errorContent;
        throw new Error(reason);
    }

    const result = await res.json();
    return result;
}

async function patch(endpoint, params = "", data) {
    const apiUrl = params ? `${endpoint}/${params}` : endpoint;
    const bodyData = JSON.stringify(data);
    console.log(`%cPATCH 요청: ${apiUrl}`, "color: #059c4b;");
    console.log(`%cPATCH 요청 데이터: ${bodyData}`, "color: #059c4b;");

    const res = await fetch(apiUrl, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: bodyData,
        credentials: "include",
    });

    if (!res.ok) {
        const errorContent = await res.json();
        const { reason } = errorContent;
        throw new Error(reason);
    }

    const result = await res.json();
    return result;
}

async function del(endpoint, params = "", data = {}) {
    const apiUrl = params ? `${endpoint}/${params}` : endpoint;
    const trimmedUrl = apiUrl.replace(/\/$/, '');
    const bodyData = JSON.stringify(data);

    console.log(`%cDELETE 요청: ${trimmedUrl}`, "color: #059c4b;");
    console.log(`%cDELETE 요청 데이터: ${bodyData}`, "color: #059c4b;");

    const res = await fetch(trimmedUrl, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        },
        body: bodyData,
        credentials: "include",
    });

    if (!res.ok) {
        const errorContent = await res.json();
        throw new Error(`HTTP error! status: ${res.status}, message: ${errorContent.reason || res.statusText}`);
    }

    if (res.status === 204) {  // No Content
        return { status: res.status };
    }

    const result = await res.json();
    return result;
}

async function postFormData(endpoint, formData) {
    const apiUrl = endpoint;
    console.log(`%cPOST FormData 요청: ${apiUrl}`, "color: #059c4b;");

    for (let [key, value] of formData.entries()) {
        console.log(`${key}:`, value);
    }

    try {
        const res = await fetch(apiUrl, {
            method: "POST",
            body: formData,
            credentials: "include",
        });

        if (!res.ok) {
            const errorContent = await res.json();
            console.error("Error response:", errorContent);
            throw new Error(errorContent.message || "서버 오류가 발생했습니다.");
        }

        return await res.json();
    } catch (error) {
        console.error("Fetch error:", error);
        throw error;
    }
}

async function patchFormData(endpoint, formData) {
    const apiUrl = endpoint;
    console.log(`%cPATCH FormData 요청: ${apiUrl}`, "color: #059c4b;");

    try {
        const res = await fetch(apiUrl, {
            method: "PATCH",
            body: formData,
            credentials: "include",
        });

        if (!res.ok) {
            const errorData = await res.json();
            throw new Error(errorData.message || "서버 오류가 발생했습니다.");
        }

        return await res.json();
    } catch (error) {
        console.error("Fetch error:", error);
        throw error;
    }
}

export { get, post, patch, del as delete, postFormData, patchFormData };
