const NCP_CLIENT_ID = CONFIG.NCP_CLIENT_ID;

const script = document.createElement('script');
script.src = `https://oapi.map.naver.com/openapi/v3/maps.js?ncpKeyId=${NCP_CLIENT_ID}`;
script.onload = function () {
    const mapOptions = {
        center: new naver.maps.LatLng(37.3595704, 127.105399),
        zoom: 10
    };
    new naver.maps.Map('map', mapOptions);
};
document.head.appendChild(script);
