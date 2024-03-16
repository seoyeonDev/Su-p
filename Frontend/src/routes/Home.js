import React from 'react';
import { useState, useEffect } from 'react';

function Home() {
    const [message, setMessage] = useState([]);

    useEffect(() => {
        fetch("/test")
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                setMessage(data);
            })
    }, []);


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
        </div>


    );

}

export default Home;