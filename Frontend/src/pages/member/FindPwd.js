import React, { Component } from 'react';

class FindPwd extends Component {
  

    render() {
        return (
            <div>
                <h1>비밀번호 찾기</h1>
                <div>
                    ID
                    <input type="text" />
                </div>
                <div>
                    이메일
                    <input type="text" />
                </div>
                <div>
                    인증번호
                    <input type="text" />
                </div>
                <button>비밀번호 찾기</button>
            </div>
        );
    }
}



export default FindPwd;
