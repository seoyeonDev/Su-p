import React from 'react';
import { useState } from 'react';
import axios from 'axios';
import StudyList from '../pages/studygroup/StudyList';

function Home() {

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
                <form onSubmit={handleSubmit}>
                    <label>
                        <input type="text" value={inputValue} onChange={handleChange}/>
                    </label>
                    <button type="submit">제출</button>
                </form>
            </div>



            <main>
                <StudyList/>
            </main>

        </div>


    );

}

export default Home;