import React, {Component, useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import axios from "axios";

const MyStudyDetailHome = ({selectedContent, group_id, user_id}) => {

    const [content, setContent] = useState(['']);   // 전체 그룹정보
    const [startdate, setStartdate] = useState(['']);   // 시작일
    const [enddate, setEnddate] = useState(['']);   // 종료일
    const [penalty, setPenalty] = useState(['']);   // 목표
    const [mainlog, setMainlog] = useState([]);   // 스터디로그 5개

    useEffect(() => {
        if(selectedContent === 'HOME'){
            const fetchGroupInfo = async () => {
                await getGroupInfo(group_id,user_id);
                await getGroupLog5(group_id);
            };
            fetchGroupInfo().then(r => {console.log('성공 @@')});
        }
        
    }, [group_id, user_id, selectedContent]);


    const getGroupInfo = async(group_id, user_id) => {
        console.log('getGroupInfo');
        axios.get('http://localhost:8080/studygroup/studyDetail',{
            params : {
                group_id : group_id,
                user_id : user_id   // 그룹장일 경우 다른 헤더 반환
            }
        }) // 그룹 정보
            .then (response => {
                if (response.status === 200){
                    // alert('그룹 정보를 가져왔습니다');
                    console.log(response.data);
                    setContent(response.data);

                    setStartdate(response.data.vo.startdate);
                    setEnddate(response.data.vo.enddate);
                    setPenalty(response.data.vo.penalty);

                }else {
                    alert(response.data);
                    console.log(response.data);
                }
            })

    }

    const getGroupLog5 = async (group_id) => {
        console.log('getGroupLog5')
        axios.get('http://localhost:8080/studylogs/getMainLog', {
            params : {
                group_id : group_id
            }
        })
            .then (response => {
                if (response.status === 200){
                    // alert('get main log 555 ');
                    console.log(response.data);
                    setMainlog(response.data)
                }else {
                    alert(response.data);
                }
            })
    }


    return (
        <div className={'common-content'}>
            {selectedContent === 'HOME' &&
                <p>
                    {/*{JSON.stringify(content)}*/}
                    {startdate} <br/>
                    {enddate} <br/>
                    {penalty} <br/>
                    <br/>
                    <p> 글ID, 제목, 글쓴이, 날짜 </p>
                    {mainlog.map((item)=> (
                        <div key={item.post_id}>
                            <p>{item.post_id}, {item.user_id}, {item.title}, {item.create_date}</p>
                        </div>
                    ))}
                </p>
            }

        </div>
    );
}

MyStudyDetailHome.propTypes = {
    selectedContent: PropTypes.string.isRequired,
    group_id: PropTypes.string.isRequired,
    user_id: PropTypes.string.isRequired,
};


export default MyStudyDetailHome;