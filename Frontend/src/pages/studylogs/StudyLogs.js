import React, {useState} from 'react';
import axios from 'axios';
import {Link} from "react-router-dom";

function StudyLogs() {

    const [groupId, setGroupId] = useState('');
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [file, fileUpload] = useState('');
    const [img, imgUpload] = useState('');

    const studyLogsVo = {
        user_id : localStorage.getItem("user_id")
        // , group_id : groupId
        , group_id : '2406300002'
        , title : title
        , content : content
        , file : file
        , img : img

    }

    const handlerTitle = (e) => {
        setTitle(e.target.value);
    }

    const handlerContent = (e) => {
        setContent(e.target.value);
    }

    const handlerGroupId = (e) => {
        setGroupId(e.target.value);
    }

    const postSave = () => {
        axios.post('http://localhost:3000/studylogs/insert', studyLogsVo)
            .then(response => {
                if (response.status === 200){
                    alert('작성 성공');
                } else {
                    alert('실패');
                }
            }).catch((err)=>{
                console.log('에러 ', err)
        })
    }


    return (
        <div>
            <div>
                제목
            </div>
            <div>
                <input type={"text"} id = "title" onChange={handlerTitle}/>
            </div>
            <div>
                내용
            </div>
            <div>
                <input type={"textarea"} id={"content"} onChange={handlerContent}/>
                <input type={"hidden"} id={"groupId"} onChange={handlerGroupId}/>
            </div>
            <div>
                <button type={"button"} onClick={postSave}> 저장</button><br/>


            </div>
        </div>

    );
}

export default StudyLogs;