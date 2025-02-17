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

/**
 * 사용자가 'Yes' 또는 'No'를 선택할 수 있는 확인 모달을 표시한다.
 * 사용자가 선택한 결과에 따라 Promise를 반환한다.
 * @param {string} title - 모달창의 제목
 * @param {string} content - 모달창에 표시할 내용
 * @param {string} alertType - 모달의 아이콘 종류 ('warning', 'error', 등)
 * @param {string} yes - 'Yes' 버튼 텍스트
 * @param {string} no - 'No' 버튼 텍스트
 * @returns {Promise} - 사용자가 'Yes' 또는 'No'를 클릭한 결과를 Promise로 반환
 */
export function showConfirmationAlert(title, content, alertType, yes, no) {
    return new Promise((resolve, reject) => {
        swal({
            title: title,                // 모달창 제목
            text: content,               // 모달창 내용
            icon: alertType,             // 아이콘 (선택 사항)
            buttons: [no, yes],          // 버튼
        }).then((willConfirm) => {
            if (willConfirm) {  // yes를 클릭했을 시
                resolve(true);  // Promise로 true 반환
            } else {            // no를 클릭했을 시
                resolve(false); // Promise로 false 반환
            }
        }).catch((error) => {
            reject(error);  // 예외 처리 (필요 시)
        });
    });
}

