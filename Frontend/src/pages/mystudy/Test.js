// App.js
import React, {useEffect, useState} from 'react';
import Header from './MyStudyHeader';
import Content from './MyStudyList';
import axios from "axios";
import Chart from 'react-apexcharts';
import MyStudyHeader from "./MyStudyHeader";
import MyStudyList from "./MyStudyList";


const Test = () => {
    // const [selectedContent, setContents] = useState([]);
    const [selectedContent, setSelectedContent] = useState(['전체 스터디']);
    const [msg, setMsg] = useState('');
    const [allAttendance, setAllAttendace] = useState(0);
    const [showCharts, setShowCharts] = useState(true);
    const [chartOptions, setChartOptions] = useState({
        series: [],
        chart: {
            type: 'donut',
        },
        labels: ['결석','출석']
    });

    // windowonload
    useEffect(() => {
        handleSelect('전체 스터디','','','');
    },[]);

    // const getJoinedList = () => {
    //     console.log('selectJoinedList')
    //     axios.get('http://localhost:3000/joinedgroup/joinedList')
    //         .then (response => {
    //             if (response.status === 200){
    //                 setMsg(response.data.msg);
    //                 alert('목록을 가져왔습니다 !');
    //                 setSelectedContent((response.data));    // 응답 저장
    //
    //             }else{
    //                 setMsg (response.data);
    //                 alert(response.data);
    //             }
    //         })
    // }


    // 헤더 조건별 목록 조회
    const handleSelect = async(buttonName, joinstatus, role, status) => {
        console.log('selectedTest : ',joinstatus,role,status);
        try {
            const response = await axios.get('http://localhost:3000/joinedgroup/selectList',{
                params: {
                    joinstatus: joinstatus,
                    role: role,
                    status: status
                }
            });
            setSelectedContent(response.data.list);
            console.log(response.data)
            if(buttonName === '전체 스터디'){
                setShowCharts(true);
                await getAllAttendance();
            } else {
                setShowCharts(false);
            }
        } catch (error) {
            console.error('Error fetching list:', error);
        }

    }

    // 전체 그룹 평균 출석율
    const getAllAttendance = async () => {
        console.log("getAllAttendance");
        axios.get('http://localhost:8080/joinedgroup/overallAttendance', {
            params: {
                user_id : localStorage.getItem("user_id")
            }
        })
            .then (response => {
                if (response.status === 200){
                    setAllAttendace(response.data.attendance);
                    console.log("attendance " + allAttendance);
                    setChartOptions(prevOptions => ({
                        ...prevOptions,
                        series: [100 - response.data.attendance, response.data.attendance]
                    }));
                }
            })
    }



    return (
        <div>
            <MyStudyHeader title = "나의 스터디" onSelect={handleSelect} />
            {showCharts.valueOf(true)&& (
                <div id = "Attendance" className="apex-charts">
                    <Chart options={chartOptions} series={chartOptions.series} type="donut" height={350} />
                </div>
            )}

            <MyStudyList selectedContent={selectedContent}/>
        </div>
    );
};

export default Test;
