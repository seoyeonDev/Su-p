import React, { Component } from 'react';


class FindId extends Component {
    render() {
        return (
            <div>
                <h1>아이디 찾기</h1>
                <div>
                    이름
                    <input type="text" />
                </div>
                <div>
                    이메일
                    <input type="text" />
                </div>
                <button>아이디 찾기</button>
            </div>
        );
    }
}



export default FindId;
