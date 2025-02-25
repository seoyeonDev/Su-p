import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../../styles/components/Member.css';
import { getIdFromLocalStorage } from "../Common";

const Info = () => {
    const [imageUrl, setImgUrl] = useState('');
    const [userInfo, setUserInfo] = useState(null);
    const [editNickNm, setEditNickNm] = useState('');
    const [editEmail, setEditEmail] = useState('');
    const [editName, setEditName] = useState('');
    const [inputNickNm, setInputNickNm] = useState('');
    const [inputEmailNum, setInputEmailNum] = useState('');
    const [inputName, setInputName] = useState('');
    const [email, setEmail] = useState('');
    const [emailNum, setEmailNum] = useState('');
    const [file, setFile] = useState('');
    const [isEditing, setIsEditing] = useState(false);
    const [emailTime, setEmailTime] = useState(10 * 60);        // 이메일 10분
    const [emailSent, setEmailSent] = useState(false);          // 이메일 발송됐을 때 타이머 보이게 만들기
    const [isDeleteImg, setIsDeleteImg] = useState(false);      // 기존 프로필 파일 삭제 여부
    const [isDefaultImg, setIsDefaultImg] = useState(false);    // default img인지
    const [defaultImg, setDefaultImg] = useState();             // default img 경로 설정 
    const user_id = getIdFromLocalStorage();

    useEffect(() => {
        if (emailTime > 0) {
            const timer = setTimeout(() => setEmailTime(emailTime - 1), 1000); // 1초 후 실행 
            return () => clearTimeout(timer);
        }
    }, [emailTime])

    useEffect(() => {
        fetchDefaultImage();  // 기본 이미지 불러오기 (저장된 사진을 기본 이미지로 변경할 때 사용)
        fetchImage();         // 사용자의 프로밀 이미지 불러오기
        fetchData();          // 사용자의 정보 불러오기 
    }, []);

    /**
    * 기본 이미지 불러오기 (저장된 사진을 기본 이미지로 변경할 때 사용)
    */
    const fetchDefaultImage = async () => {
        try {
            const response = await axios.get(`http://localhost:3000/member/getDefaultImage`, {
                responseType: 'arraybuffer',
            });

            if (response.status !== 200) {
                throw new Error("이미지를 불러올 수 없습니다.");
            }

            const mediaType = response.headers['content-type']; // 헤더에서 content-type을 추출
            const blob = new Blob([response.data], { type: mediaType });
            const imageUrl = URL.createObjectURL(blob);

            setDefaultImg(imageUrl);
        } catch (error) {
            console.log("error: " + error);
        }
    }

    /**
     * 사용자의 프로밀 이미지 불러오기
     */
    const fetchImage = async () => {
        try {
            const response = await axios.get(`http://localhost:3000/member/getImage/${user_id}`, {
                responseType: 'arraybuffer',
            });

            if (response.status !== 200) {
                throw new Error("이미지를 불러올 수 없습니다.");
            }

            const mediaType = response.headers['content-type']; // 헤더에서 content-type을 추출
            const blob = new Blob([response.data], { type: mediaType });
            const imageUrl = URL.createObjectURL(blob);

            setImgUrl(imageUrl);
        } catch (error) {
            console.log("error: " + error);
        }
    }

    /**
     * 사용자 정보 불러오기
     */
    const fetchData = async () => {
        try {
            const response = await axios.get('http://localhost:3000/member/mypage', {
                params: {
                    user_id: user_id
                }
            });
            setEditNickNm(1);
            setEditEmail(1);
            setUserInfo(response.data);
            setInputNickNm(response.data.member.nickname);
            setInputName(response.data.member.name);
            setEmail(response.data.member.email);

            if(response.data.member.profile_img === null){
                setIsDefaultImg(true);
            }

            localStorage.setItem('userName', response.data.member.name);
            localStorage.setItem('userEmail', response.data.member.email);

        } catch(error) {
            console.log("error: " + error);
        }
    };


    const handleInputNickName = (event) => {
        setInputNickNm(event.target.value);
        setEditNickNm(0);
    }

    const handleInputName = (event) => {
        setInputName(event.target.value);
        // setEditName(0);
    }

    const checkNickNm = () => {
        axios.get('http://localhost:3000/member/checkNickNm/' + inputNickNm)
            .then(response => {
                if(response.data === 0) {
                    alert('사용 가능한 닉네임입니다.');
                    setEditNickNm(1);
                } else {
                    alert('이미 사용 중인 닉네임입니다.');
                    setEditNickNm(0);
                }
            });
    };

    const handleInputEmail = (event) => {
        setEmail(event.target.value);
        setEditEmail(0);
    }

    // 이메일 변경
    const changeEmail = () => {
        setEditEmail(0);
        if(!email.includes("@") || email.includes(" ")) {
            alert('유효한 이메일을 입력해주세요.');
        } else {
            setIsEditing(true);
            axios.post(`http://localhost:3000/mail/mailSend?mail=${email}`)
                .then(response => {
                    console.log(response.data);
                    if(response.data.success){
                        setEmailNum(response.data.number);
                        setEmailTime(10 * 60);
                        setEmailSent(true);
                        alert('입력하신 이메일로 인증번호를 발송하였습니다.');
                    } else {
                        alert('인증번호 발송에 실패하였습니다.');
                    }
                }).catch(error => {
                    console.log(error);
                });
        }
    };

    // 이메일 인증번호 확인
    const checkCode = () => {
        axios.get(`http://localhost:3000/mail/mailCheck`,{
            params: {
                userNumber : inputEmailNum
            }
        })
        .then(response => {
            if (response.data === true && (minutes > 0 || seconds > 0)) {
                setEditEmail(1)
                alert('이메일 인증에 성공하였습니다.');
                setEmailSent(false);
            } else if(minutes === 0 && seconds === 0) {
                alert('이메일 유효 시간을 확인해주세요.');
                setEmailSent(false);
            } else {
                alert('이메일 인증에 실패하였습니다.');
                setEmailSent(false);
            }
        }). catch(error => {
            console.log(error);
        })
    };

    const vo = {
        user_id : user_id,
        name : inputName,
        nickname : inputNickNm,
        email : email,
        profile_img: ""
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("vo", new Blob([JSON.stringify(vo)], { type: "application/json" }));
    formData.append("isDeleteImg", new Blob([JSON.stringify(isDeleteImg)], { type: "application/json" }));

    const changeInfo = () => {
        console.log("editEmail: " + editEmail);
        if(editNickNm === 0 && editEmail === 1) {
            alert('닉네임 중복 확인을 진행해주세요.');
        } else if(editNickNm === 1 && editEmail === 0) {
            alert('이메일 인증을 진행해주세요.');
        } else if(editNickNm === 1 && editEmail === 1) {
            axios.post('http://localhost:3000/member/update', formData, {
                headers: {
                    'Content-Type' : 'multipart/form-data'
                }
            })
                .then(response => {
                    alert('회원 정보가 수정되었습니다.');
                });
        }
    };

    const fileTypes = [
        'image'
    ]

    const handleFileChange = (event) => {
        const selectedFile = event.target.files[0];
        if (selectedFile) {
            console.log(selectedFile.type);

            // 파일 타입이 사진일 경우에만 등록 가능
            const type = selectedFile.type;
            if (!fileTypes.includes(type.substring(0,type.indexOf('/')))) {
                event.target.value = '';
                alert('사진 파일만 첨부가 가능합니다.');
                return;
            }
            // 미리보기 이미지 업데이트
            const reader = new FileReader();
            reader.onloadend = () => {
                setImgUrl(reader.result);
                setIsDefaultImg(false);
            };
            reader.readAsDataURL(selectedFile);

            // 파일 상태 업데이트
            setFile(selectedFile);
        }
    }

    if (!userInfo || !userInfo.member) {
        return <div>No user info available</div>;
    }

    // userInfo가 null이 아니고, userInfo.member도 존재할 때 구조 분해 할당
    const { member } = userInfo;
    const minutes = Math.floor((emailTime || 0) / 60);
    const seconds = (emailTime || 0) % 60;

    /**
     * 기본 이미지로 변경하기
     */
    const handleDeleteImage = () => {
        setImgUrl('');          // 미리보기 이미지 삭제
        setFile(null);          // 파일 상태 삭제
        setImgUrl(defaultImg);  // 기본 이미지로 변경
        setIsDeleteImg(true);   // 기존 이미지를 삭제했음을 표시  
        setIsDefaultImg(true);  // 현재 이미지가 기본 이미지임을 표시

        document.getElementById("profileImg").value = ""; // 파일명을 빈 값으로 설정 
    };

    return (
        <div>
            <h2>내 정보</h2>
            <div className='info-area'>
                <div className='info-area-image'>
                    {imageUrl && (
                        <>
                            <img src={imageUrl} style={{ width: '80%', height: 'auto' }} />

                            {!isDefaultImg && (
                                <>
                                    <button onClick={handleDeleteImage}>기본 이미지로 변경</button>
                                </>
                            )}
                        </>
                    )}

                    <input type="file" id="profileImg" onChange={handleFileChange} />
                </div>

                <div className='info-area-type'>
                    <div className='info-area-type-visible'>
                        아이디  <br/>
                        <span className='highlight'> 이름</span>    <br/>
                        <span className='highlight'> 닉네임 </span>  <br/>
                        <span className='highlight'> 이메일 </span> <br/>
                    </div>
                    <div className='info-area-type-invisible'>
                        인증번호
                    </div>
                </div>

                <div className='info-area-myinfo'>
                    <div className='info-area-myinfo-visible'>
                        <input type='text' id='userId' name='userId' value={member.user_id} readOnly /> <br/>
                        <input type='text' id='userName' name='userName' onChange={handleInputName} defaultValue={member.name}  /> <br/>
                        <input type='text' id='userNickNm' name='userNickNm' onChange={handleInputNickName} defaultValue={member.nickname} /> <br/>
                        <input type='text' id='userEmail' name='userEmail' onChange={handleInputEmail} defaultValue={member.email} /> <br/>
                    </div>
                    <div className='info-area-myinfo-invisible'>
                        <input type='text' id='verificationCode' name='verificationCode' onChange={(e) => setInputEmailNum(e.target.value)} disabled={!isEditing} />
                    </div>
                    <div id="emailTimeChk" style={{display: emailSent ? 'block' : 'none'}}>
                        {minutes.toString().padStart(2, '0')}:{seconds.toString().padStart(2, '0')}
                    </div>
                </div>

                <div className='info-area-button'>
                    <div className='info-area-button-visible'>
                        <br/>
                        <br/>
                        <button onClick={checkNickNm}>중복확인</button> <br/>
                        <button onClick={changeEmail}>변경</button> <br/>
                    </div>
                    <div className='info-area-button-invisible'>
                        <button onClick={checkCode}>인증하기</button> <br/>
                    </div>
                </div>

            </div>
            <div className='info-area-change'>
                <button onClick={changeInfo}>수정</button>
            </div>
        </div>
    );
};

export default Info;