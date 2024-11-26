import React, {useState} from 'react';
import axios from "axios";
import MyStudyDetailHeader from "./MyStudyDetailHeader";
import myStudyDetailHome from "./MyStudyDetailHome";
import MyStudyDetailHome from "./MyStudyDetailHome";
import Test from "../Test";
import MyStudyDetailMyList from "./MyStudyDetailMyList";
import MyStudyDetailGroupInfo from "./MyStudyDetailGroupInfo";

const MyStudyDetail = () => {
    const [selectedContent, setSelectedContent] = useState([]);

    // const getGroupInfo = async(group_id, user_id) => {
    //     console.log('getGroupInfo');
    //     axios.get('http://localhost:8080/studygroup/studyDetail',{
    //         params : {
    //             group_id : '2411020001',
    //             user_id : user_id   // 그룹장일 경우 다른 헤더 반환
    //         }
    //     }) // 그룹 정보
    //         .then (response => {
    //             if (response.status === 200){
    //                 alert('그룹 정보를 가져왔습니다');
    //                 setSelectedContenct(response.data);
    //             }else {
    //                 alert(response.data);
    //             }
    //         })
    //
    // }


    // const handleSelect = async(buttonName) => {
    //     try {
    //         alert('handleSelect')
    //         await getGroupInfo();
    //     } catch (error){
    //         console.log('ERROR MyStudyDetail : ', error);
    //     }
    // }


    return (
        <div className={'common-content-container'}>
            <div className={'common-content'}>
                <h1>나의 스터디</h1>
                <MyStudyDetailHeader title="스터디명" onSelect={setSelectedContent}/>


                <MyStudyDetailHome selectedContent={selectedContent}/>
                <MyStudyDetailMyList selectedContent={selectedContent}/>
                <MyStudyDetailGroupInfo selectedContent={selectedContent}/>
            </div>
        </div>

    )
}
export default MyStudyDetail;