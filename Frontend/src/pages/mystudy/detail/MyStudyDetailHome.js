import React, {Component, useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import axios from "axios";
import Chart from 'react-apexcharts';

const MyStudyDetailHome = ({selectedContent,groupInfo, group_id, user_id}) => {

    const [content, setContent] = useState(['']);   // 전체 그룹정보
    const [startdate, setStartdate] = useState(['']);   // 시작일
    const [enddate, setEnddate] = useState(['']);   // 종료일
    const [penalty, setPenalty] = useState(['']);   // 목표
    const [mainlog, setMainlog] = useState([]);   // 스터디로그 5개
    const [attendance, setAttendance] = useState(0);
    const [showCharts, setShowCharts] = useState(false);
    const [chartOptions, setChartOptions] = useState({
        series: [],
        chart: {
            type: 'donut',
        },
        labels: ['결석','출석']
    });

    useEffect(() => {
        if(selectedContent === 'HOME'){
            const fetchGroupInfo = async () => {
                await getGroupInfo(group_id,user_id);
                await getGroupLog5(group_id);
                await getGroupAttendance(group_id);
            };
            fetchGroupInfo().then(r => {console.log('조회 성공')});
        }
        
    }, [group_id, user_id, selectedContent,groupInfo]);

    const getGroupInfo = async() =>{
        setStartdate(groupInfo.startdate);
        setEnddate(groupInfo.enddate);
        setPenalty(groupInfo.penalty);
    }

    // const getGroupInfo = async(group_id, user_id) => {
    //     console.log('getGroupInfo');
    //     axios.get('http://localhost:8080/studygroup/studyDetail',{
    //         params : {
    //             group_id : group_id,
    //             user_id : user_id   // 그룹장일 경우 다른 헤더 반환
    //
    //         }
    //     }) // 그룹 정보
    //         .then (response => {
    //             if (response.status === 200){
    //                 // alert('그룹 정보를 가져왔습니다');
    //                 console.log(response.data);
    //                 setContent(response.data);
    //
    //                 setStartdate(response.data.vo.startdate);
    //                 setEnddate(response.data.vo.enddate);
    //                 setPenalty(response.data.vo.penalty);
    //
    //             }else {
    //                 alert(response.data);
    //                 console.log(response.data);
    //             }
    //         })
    //
    // }

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

    // 상세 그룹 평균 출석율
    const getGroupAttendance = async (group_id) => {
        console.log("getAttendance");
        axios.get('http://localhost:8080/joinedgroup/individualStudyAttendance', {
            params: {
                user_id : localStorage.getItem("user_id"),
                group_id : '2411020001' // TODO 넘겨주는 group_id로 변경하기
            }
        })
            .then (response => {
                if (response.status === 200){
                    setAttendance(response.data.studyAttendanceResult.attendance);
                    console.log("attendance " + attendance);
                    setChartOptions(prevOptions => ({
                        ...prevOptions,
                        series: [100 - attendance, attendance]
                    }));
                }
            })
    }


    return (
        <div className={'common-content'}>
            {selectedContent === 'HOME' &&
                <div id = "Attendance" className="apex-charts">
                    <Chart options={chartOptions} series={chartOptions.series} type="donut" height={350} />
                </div>
            }
            {selectedContent === 'HOME' &&
                <p>
                    {/*{JSON.stringify(content)}*/}
                    시작일 : {startdate} <br/>
                    종료일 : {enddate} <br/>
                    페널티 : {penalty} <br/>
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