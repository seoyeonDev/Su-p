import React, {Component, useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import axios from "axios";
import Chart from 'react-apexcharts';
import {getIdFromLocalStorage} from "../../Common";

const MyStudyDetailMyAttendance = ({selectedContent, group_id, user_id, groupInfo}) => {

    const [content, setContent] = useState(['']);   // 전체 그룹정보
    const [startdate, setStartdate] = useState(['']);   // 시작일
    const [enddate, setEnddate] = useState(['']);   // 종료일
    const [penalty, setPenalty] = useState(['']);   // 목표

    useEffect(() => {
        if (selectedContent === '나의 출석') {
            const fetchInfo = async()=> {
                await getGroupAttendance(group_id);
            };
            fetchInfo().then(r => {console.log('성공 @@')});
        }
    },[group_id]);


    // 상세 그룹 평균 출석율
    const getGroupAttendance = async (group_id) => {
        console.log("getAttendance");
        axios.get('http://localhost:8080/joinedgroup/detailGroupAttendance', {
            params: {
                user_id: getIdFromLocalStorage(),
                group_id : group_id
            }
        })
            .then (response => {
                if (response.status === 200){
                    console.log(response.data)
                    setContent(response.data)

                }
            })
    }


    return (
        <div className={'common-content'}>
            {selectedContent === '나의 출석' &&
                <p>
                    {/*{JSON.stringify(content)}*/}
                    {groupInfo.startdate} <br/>
                    {groupInfo.enddate} <br/>
                    {groupInfo.penalty} <br/>
                    <br/>
                    <p> 순번, 그룹ID, 작성자, 제출횟수, 주차, 기준 패널티, 출석 여부 </p>
                    {content.map((item)=> (
                        <div key={item.row_number}>
                            <p>{item.row_number}, {item.group_id}, {item.user_id}, {item.count}, {item.assigncycle}, {item.penalty}, {item.penalty_chk}</p>
                        </div>
                    ))}
                </p>
            }

        </div>
    );
}

MyStudyDetailMyAttendance.propTypes = {
    selectedContent: PropTypes.string.isRequired,
    group_id: PropTypes.string.isRequired,
    user_id: PropTypes.string.isRequired,
};


export default MyStudyDetailMyAttendance;