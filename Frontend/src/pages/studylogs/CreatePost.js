import React, {useState, useEffect} from 'react';
import axios from 'axios';
import { useNavigate, useLocation} from "react-router-dom";

import '../../styles/studylogs/CreateStudylogs.css';

function CreatePost() {
    const navigate = useNavigate();
    const location = useLocation(); // 현재 URL 정보를 가져옴
    const searchParams = new URLSearchParams(location.search);
    const groupId = searchParams.get("groupId");
    const postId = searchParams.get("postId");

    const [groupName, setGroupName] = useState('');
    const [isEditMode, setIsEditMode] = useState(false);

    const [postData, setPostData] = useState({
        title: '',
        content: '',
        user_id: localStorage.getItem('user_id') || '',
        post_id: '', 
        image: null,
        file: null,
        prevImageSeq: null, // 기존 이미지 file_seq
        prevImageId: null, // 기존 이미지 file_id
        prevImageName: null, // 기존 파일 file_name
        prevFileSeq: null, // 기존 파일 file_seq
        prevFileId: null, // 기존 파일 file_id
        prevFileName: null, // 기존 파일 file_name
        deleteFiles: [], // 삭제 요청할 파일들의 객체 배열
    });

    const handleChange = (key, value) => {
        setPostData((prev) => ({
            ...prev,
            [key]: value !== undefined && value !== null ? value : '',
        }));
    };

    // 이미지가 변경되었을시
    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setPostData(prev => ({
                ...prev,
                image: file,
                prevImageSeq: null,
                prevImageId: null,
                prevImageName: null
            }));
        }
    };

    // 파일이 변경되었을시
    const handleFileChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setPostData(prev => ({
                ...prev,
                file,
                prevFileSeq: null,
                prevFileId: null,
                prevFileName: null
            }));
        }
    }

    // 이미지 삭제 버튼 클릭시
    const handleRemoveImage = () => {
        setPostData(prev => ({
            ...prev,
            image: null,
            deleteFiles: [...prev.deleteFiles, {
                file_seq: prev.prevImageSeq,
                file_id: prev.prevImageId
            }], // 삭제할 이미지 파일 정보 추가
            prevImageSeq: null,  // 이미지 삭제 후 seq와 id도 null 처리
            prevImageId: null,
            prevImageName: null
        }));
    };

    // 파일 삭제 버튼 클릭시
    const handleRemoveFile = () => {
        setPostData(prev => ({
            ...prev,
            file: null,
            deleteFiles: [...prev.deleteFiles, {
                file_seq: prev.prevFileSeq,
                file_id: prev.prevFileId
            }], // 삭제할 파일 정보 추가
            prevFileSeq: null, // 파일 삭제 후 seq와 id도 null 처리
            prevFileId: null,
            prevFileName: null
        }));
    };

    // studygroup 정보만 가져오는 useEffect
    useEffect(() => {
        if (groupId) {
            axios.get(`http://localhost:3000/studygroup/studyInfo`, { params: { 'group_id': groupId } })
                .then((response) => {
                    setGroupName(response.data.groupVo.name);
                }).catch((error) => {
                    console.error('Error fetching study info:', error);
                });
        }
    }, [groupId]);
    
    // studylog 정보 가져오는 useEffect
    useEffect(() => {
        if (groupId && postId) {
            axios.get(`http://localhost:3000/studylogs/studylogsdetail/${postId}`)
                .then((response) => {
                    setIsEditMode(true);
                    const data = response.data;
                    setPostData(prev => ({
                        ...prev,
                        title: data.vo.title,
                        content: data.vo.content,
                        post_id: data.vo.post_id
                    }));
    
                    data.fileList.forEach(file => {
                        if (file.fileType === 'KIND10') {
                            setPostData(prev => ({
                                ...prev,
                                image: file.path,
                                prevImageSeq: file.fileSeq,
                                prevImageId: file.fileId,
                                prevImageName: file.fileName + '.' + file.fileExt
                            }));
                        } else if (file.fileType === 'KIND20') {
                            setPostData(prev => ({
                                ...prev,
                                file: file.path,
                                prevFileSeq: file.fileSeq,
                                prevFileId: file.fileId,
                                prevFileName: file.fileName + '.' + file.fileExt
                            }));
                        }
                    });
                }).catch((error) => {
                    console.error('Error fetching studylog info:' + error);
                });
        }
    }, [groupId, postId]);

    // 저장 및 수정
    const handleSaveBtn = () => {
        const vo = {
            title: postData.title,
            content: postData.content,
            group_id: groupId,
            user_id: postData.user_id,
            post_id: postData.post_id // 수정 모드일 때만 필요
        };

        const formData = new FormData();

        const json = JSON.stringify(vo);
        const blob = new Blob([json], { type: "application/json" });
        formData.append("vo", blob);

        appendFileIfNeeded('images', postData.image, postData.prevImageId, formData);
        appendFileIfNeeded('files', postData.file, postData.prevFileId, formData);
        
        const url = isEditMode
            ? `http://localhost:3000/studylogs/update`
            : `http://localhost:3000/studylogs/insert`;

        console.log(url);

        const method = 'post';

        if(isEditMode){
            const deleteFilesJson = JSON.stringify(postData.deleteFiles);
            const deleteFilesBlob = new Blob([deleteFilesJson], {type: "application/json"});
            formData.append("deleteFiles", deleteFilesBlob);
        }

        // axios 요청 보내기
        axios({
            method: method,  
            url: url,
            data: formData,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
            .then((response) => {
                // 성공적인 응답 처리
                if(isEditMode){
                    // TODO. 디테일 url로 이동
                } else {
                    // TODO. 글 목록 url로 이동 
                }
                console.log(response.data);
            })
            .catch((err) => {
                // 에러 처리
                console.error('Error:', err.response);
                alert('서버와의 통신 중 오류가 발생했습니다.');
            });
    }

    // 파일이 수정됐는지 안 됐는지 구분하기 위함
    const appendFileIfNeeded = (key, file, prevFileId, formData) => {
        // 수정 모드일 때 : 파일 존재하고 prevFileId가 null 이라면 파일 저장해야하므로 추가 (기존 파일 삭제 후 새로운 파일 로드)
        // 처음 작성할 때 : 모든 파일은 저장해야하므로 추가
        if (file && (isEditMode ? prevFileId == null : true)) {
            formData.append(key, file);  // 해당 키로 파일을 formData에 추가
        }
    };    

    // 뒤로가기
    const handleCancle = () => {
        navigate(-1);
    }

    return (
        <div className='common-content-container'>
            <div className='common-content'>
                <h1>{groupName}</h1>

                <div>
                    {/* 제목 입력 폼 */}
                    <div className="input-container">
                        <label htmlFor="title" className="input-label">제목</label>
                        <input
                            type="text"
                            id="title"
                            className="input-field"
                            placeholder="제목을 입력하세요"
                            value={postData.title}
                            onChange={(e) => handleChange('title', e.target.value)}
                        />
                    </div>

                    {/* 이미지 첨부 */}
                    <div className="image-container">
                        <label htmlFor="img" className="input-label">이미지 첨부</label>
                        {postData.prevImageSeq && postData.prevImageId ? (
                            <div>
                                <img src={postData.image} alt="기존 이미지"  style={{ width: '400px', marginTop: '10px' }} />
                                <span>{postData.prevImageName}</span>
                                <button type="button" onClick={handleRemoveImage}>삭제</button>
                            </div>
                        ) : (
                            <input
                                type="file"
                                id="img"
                                accept="image/*"
                                className="input-field"
                                onChange={handleImageChange}
                            />
                        )}
                    </div>

                    {/* 내용 입력 폼 */}
                    <div className="input-container">
                        <label htmlFor="content" className="input-label">내용</label>
                        <textarea
                            id="content"
                            className="input-field"
                            placeholder="공부한 내용을 적어주세요."
                            value={postData.content}
                            onChange={(e) => handleChange('content', e.target.value)}
                        />
                    </div>

                    {/* 파일 첨부 */}
                    <div className="file-container">
                        <label htmlFor="file" className="input-label">파일 첨부</label>
                        {postData.prevFileSeq && postData.prevFileId ? (
                            <div>
                                {/* TODO. 파일 다운로드 요청 url */}
                                {postData.prevFileName}
                                <button type="button" onClick={handleRemoveFile}>삭제</button>
                            </div>
                        ) : (
                            <input
                                type="file"
                                id="file"
                                className="input-field"
                                onChange={handleFileChange}
                            />
                        )}
                    </div>
                </div>

                <div className="button-container">
                    <button type="button" onClick={handleCancle}>취소</button>
                    <button type="button" onClick={handleSaveBtn}>
                        {isEditMode ? '수정하기' : '등록하기'}
                    </button>
                </div>
            </div>
        </div>

    );
}

export default CreatePost;