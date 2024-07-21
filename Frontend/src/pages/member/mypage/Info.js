import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Info = () => {
    const [imageUrl, setImgUrl] = useState('');
    const [userInfo, setUserInfo] = useState(null);
    const [editNickNm, setEditNickNm] = useState('');
    const [editEmail, setEditEmail] = useState('');
    const [inputNickNm, setInputNickNm] = useState('');
    const [email, setEmail] = useState('');
    const [emailNum, setEmailNum] = useState('');
    const [inputEmailNum, setInputEmailNum] = useState('');
    const [file, setFile] = useState('');
    const [isEditing, setIsEditing] = useState(false);
    const user_id = localStorage.getItem('user_id');

    useEffect(() => {
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
                setEmail(response.data.member.email);
                localStorage.setItem('userName', response.data.member.name);
                localStorage.setItem('userEmail', response.data.member.email);
                
                setImgUrl('http://localhost:3000/member/getImage/' + user_id);
            } catch(error) {
                console.log("error: " + error);
            }
        };

        fetchData();
    }, []);

    const handleInputNickName = (event) => {
        setInputNickNm(event.target.value);
        setEditNickNm(0);
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
                    alert('입력하신 이메일로 인증번호를 발송하였습니다.');
                } else {
                    alert('인증번호 발송에 실패하였습니다.');
                }
            }).catch(error => {
                console.log(error);
            });
        }
    };

    const checkCode = () => {
        if((inputEmailNum === emailNum) && inputEmailNum !== '') {
            setEditEmail(1);
            alert('이메일 인증에 성공하였습니다.');
        } else {
            alert('이메일 인증에 실패하였습니다.');
        }
    };

    const vo = {
        user_id : user_id,
        nickname : inputNickNm,
        email : email,
        profile_img: ""
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("vo", new Blob([JSON.stringify(vo)], { type: "application/json" })); 

    const changeInfo = () => {
        console.log("editEmail: " + editEmail);
        if(editNickNm === 0 && editEmail === 1) {
            alert('닉네임 중복 확인을 진행해주세요.');
        } else if(editNickNm === 1 && editEmail === 0){
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

    const handleFileChange = (event) => {
        const selectedFile = event.target.files[0];
        if (selectedFile) {
          // 미리보기 이미지 업데이트
          const reader = new FileReader();
          reader.onloadend = () => {
            setImgUrl(reader.result);
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

  return (
    <div>
      <h2>내 정보</h2>
      <div className='info-area'>
        <div className='info-area-image'>
            <img src={imageUrl} style={{ width: '80%', height: 'auto' }}/>
            <input type="file" onChange={handleFileChange} />
        </div>

        <div className='info-area-type'>
            <div className='info-area-type-visible'>
                아이디  <br/>
                이름    <br/>
                닉네임  <br/>
                이메일  <br/>
            </div>
            <div className='info-area-type-invisible'>
                인증번호
            </div>
        </div>

        <div className='info-area-myinfo'>
            <div className='info-area-myinfo-visible'>
                <input type='text' id='userId' name='userId' value={member.user_id} readOnly /> <br/>
                <input type='text' id='userName' name='userName' value={member.name} readOnly /> <br/>
                <input type='text' id='userNickNm' name='userNickNm' onChange={handleInputNickName} defaultValue={member.nickname} /> <br/>
                <input type='text' id='userEmail' name='userEmail' onChange={handleInputEmail} defaultValue={member.email} /> <br/>
            </div>
            <div className='info-area-myinfo-invisible'>
                <input type='text' id='verificationCode' name='verificationCode' onChange={(e) => setInputEmailNum(e.target.value)} disabled={!isEditing} />
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