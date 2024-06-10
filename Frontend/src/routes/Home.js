import React from 'react';
import { useState, useEffect } from 'react';
import axios from 'axios';

import JoinedList from '../pages/joinedgroup/JoinedList';

function Home() {
    const [message, setMessage] = useState([]);

    useEffect(() => {
        
        axios.get('/test')
        .then((response)=>{
            setMessage(response.data);
        })
        .catch(error=>{
            console.error('Error :',error);
        })


    }, []);

    // input 값 이 변수에 넣을거임
    const [inputValue, setInputValue] = useState('');

    // input 입력값이 바뀔 때마다 inputValue에 값 저장 
    const handleChange = (event) => {
        setInputValue(event.target.value);
    }

    // 제출 버튼 눌렀을 때 
    const handleSubmit = (event) => {
        event.preventDefault();

        console.log(inputValue);

        // post('보낼 url', {보낼 데이터})
        axios.post('/test/saveData',{inputValue})
            .then(response=>{
                console.log('서버 응답 : ', response.data);
            })
            .catch(error => {
                console.error('error :', error);
            })

    }


    return (

        <div>
            <div> 
                home화면 <br/>
            </div>
            <div>
                {message}
            </div>
            <div>
                위 인사는 스프링에서 가져온 것. 
            </div>
            <div>
                <form onSubmit={handleSubmit}>
                    <label>
                        <input type="text" value={inputValue} onChange={handleChange}/>
                    </label>
                    <button type="submit">제출</button>
                </form>
            </div>
            <div>
                서연
                <JoinedList/>
            </div>

        </div>


    );

}

export default Home;