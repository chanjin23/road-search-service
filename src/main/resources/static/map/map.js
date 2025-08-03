const NCP_CLIENT_ID = CONFIG.NCP_CLIENT_ID;

// URL 에서 좌표 파라미터 추출
const params = new URLSearchParams(window.location.search);
const xpos = parseFloat(params.get('xpos')) || 127.105399;
const ypos = parseFloat(params.get('ypos')) || 37.3595704;

// 네이버 지도 API 동적 로딩
const script = document.createElement('script');
script.src = `http://oapi.map.naver.com/openapi/v3/maps.js?ncpKeyId=${CONFIG.NCP_CLIENT_ID}`;
script.onload = function () {
    new naver.maps.Map('map', {
        center: new naver.maps.LatLng(ypos, xpos),
        zoom: 30
    });
};
document.head.appendChild(script);

document.getElementById('closeBtn').addEventListener('click', function () {
    history.back();  // 이전 페이지로 이동
});
