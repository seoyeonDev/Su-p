import swal from 'sweetalert';

// localStorage 에 저장된 user_id 의 값 가져오기
export function getIdFromLocalStorage(){
    const item = localStorage.getItem("user_id");
    if (!item) return null;

    const {value, expireTime} = JSON.parse(item);
    return value;
}

/**
 * 사용자가 설정한 제목과 내용으로 알림을 표시한다.
 * 알림의 종류는 `alertType`을 통해 설정할 수 있다. (예: 성공, 오류, 경고, 정보)
 * @param {*} title 
 * @param {*} content 
 * @param {*} alertType : success, error, warning, info 로 설정 가능
 */
export function showIconAlert(title, content, alertType){
    swal(title, content, alertType);
}

/**
 * 사용자가 설정한 제목과 내용으로 기본 알림을 표시한다.
 * 이 함수는 알림의 종류를 따로 설정하지 않고 기본 알림을 띄운다.
 * @param {string} title - 알림의 제목
 * @param {string} content - 알림에 표시할 내용
 */
export function showMsgAlert(title, content){
    swal(title, content);
}