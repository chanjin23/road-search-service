function buildQueryString(params) {
    const query = new URLSearchParams(params);
    const queryString = query.toString();
    return queryString ? `?${queryString}` : "";
}

function get(endpoint, params = "") {
    const queryString = buildQueryString(params);
    const apiUrl = `${endpoint}${queryString}`;
    console.log(`%cGET 요청: ${apiUrl} `, "color: #a25cd1;");

    return new Promise((resolve, reject) => {
        $.ajax({
            url: apiUrl,
            method: "GET",
            xhrFields: { withCredentials: true },
            success: (data) => resolve(data),
            error: (xhr) => {
                try {
                    const { reason } = JSON.parse(xhr.responseText);
                    reject(new Error(reason));
                } catch {
                    reject(new Error("서버 오류가 발생했습니다. 관리자에게 문의하세요."));
                }
            }
        });
    });
}

function post(endpoint, data) {
    console.log(`%cPOST 요청: ${endpoint} `, "color: #059c4b;");
    const bodyData = JSON.stringify(data);

    return new Promise((resolve, reject) => {
        $.ajax({
            url: endpoint,
            method: "POST",
            data: bodyData,
            contentType: "application/json",
            xhrFields: { withCredentials: true },
            success: (data) => resolve(data),
            error: (xhr) => {
                const { reason } = JSON.parse(xhr.responseText);
                reject(new Error(reason));
            }
        });
    });
}

function put(endpoint, data) {
    console.log(`%cPUT 요청: ${endpoint}`, "color: #059c4b;");
    const bodyData =JSON.stringify(data);

    return new Promise((resolve, reject) => {
        $.ajax({
            url: endpoint,
            method: "PUT",
            data: bodyData,
            contentType: "application/json",
            xhrFields: { withCredentials: true },
            success: (response) => resolve(response),
            error: (xhr) => {
                const { reason } = JSON.parse(xhr.responseText);
                reject(new Error(reason));
            }
        });
    });
}


function del(endpoint, params = "", data = {}) {
    const apiUrl = params ? `${endpoint}/${params}` : endpoint;
    const trimmedUrl = apiUrl.replace(/\/$/, '');
    const bodyData = JSON.stringify(data);

    console.log(`%cDELETE 요청: ${trimmedUrl}`, "color: #059c4b;");
    console.log(`%cDELETE 요청 데이터: ${bodyData}`, "color: #059c4b;");

    return new Promise((resolve, reject) => {
        $.ajax({
            url: trimmedUrl,
            method: "DELETE",
            data: bodyData,
            contentType: "application/json",
            xhrFields: { withCredentials: true },
            success: (data, status, xhr) => {
                if (xhr.status === 204) resolve({ status: xhr.status });
                else resolve(data);
            },
            error: (xhr) => {
                const response = JSON.parse(xhr.responseText);
                reject(new Error(`HTTP error! status: ${xhr.status}, message: ${response.reason || xhr.statusText}`));
            }
        });
    });
}

function postFormData(endpoint, formData) {
    console.log(`%cPOST FormData 요청: ${endpoint}`, "color: #059c4b;");

    for (let [key, value] of formData.entries()) {
        console.log(`${key}:`, value);
    }

    return new Promise((resolve, reject) => {
        $.ajax({
            url: endpoint,
            method: "POST",
            data: formData,
            processData: false,
            contentType: false,
            xhrFields: { withCredentials: true },
            success: (data) => resolve(data),
            error: (xhr) => {
                const errorContent = JSON.parse(xhr.responseText);
                reject(new Error(errorContent.message || "서버 오류가 발생했습니다."));
            }
        });
    });
}

function patchFormData(endpoint, formData) {
    console.log(`%cPATCH FormData 요청: ${endpoint}`, "color: #059c4b;");

    return new Promise((resolve, reject) => {
        $.ajax({
            url: endpoint,
            method: "PATCH",
            data: formData,
            processData: false,
            contentType: false,
            xhrFields: { withCredentials: true },
            success: (data) => resolve(data),
            error: (xhr) => {
                const errorContent = JSON.parse(xhr.responseText);
                reject(new Error(errorContent.message || "서버 오류가 발생했습니다."));
            }
        });
    });
}

export { get, post, put, del as delete, postFormData, patchFormData };
