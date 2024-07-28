import React, { Component } from 'react';
import axios from 'axios';

class CreatePost extends Component {
    state = {
        title: '',
        content: '',
        files: [] // 모든 파일을 담을 상태 배열
    };

    handleInputChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }

    handleFileChange = (e) => {
        // 모든 파일 입력을 처리하고 상태에 업데이트
        const { files } = this.state;
        const newFiles = Array.from(e.target.files);
        this.setState({
            files: [...files, ...newFiles] // 기존 파일들과 새로운 파일들을 합침
        });
    }

    handleSubmit = (e) => {
        e.preventDefault();

        const { title, content, files } = this.state;
        const data = {
            post_id: '',
            user_id: 'seungmin501', // 테스트를 위한 예시 user_id
            group_id: '1', // 테스트를 위한 예시 group_id
            title: title,
            content: content,
            create_date: new Date().toISOString(),
            update_date: new Date().toISOString(),
            file_id: 'file_id_example',
            img_id: 'img_id_example'
        };

        console.log("Files:", files);

        const formData = new FormData();
        formData.append("vo", new Blob([JSON.stringify(data)], { type: "application/json" }));

        // 모든 파일을 FormData에 추가
        files.forEach((file) => {
            formData.append('files', file);
        });

        axios.post('http://localhost:3000/studylogs/insert', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
        .then(response => {
            console.log(response);
        })
        .catch(error => {
            console.log(error);
        });
    }

    render() {
        const { files } = this.state;

        return (
            <div>
                <form onSubmit={this.handleSubmit}>
                    <div>
                        제목 <input name="title" type="text" value={this.state.title} onChange={this.handleInputChange} />
                    </div>
                    <div>
                        내용 <input name="content" type="text" value={this.state.content} onChange={this.handleInputChange} />
                    </div>
                    <div>
                        파일1 <input type="file" multiple onChange={this.handleFileChange} />
                    </div>
                    <div>
                        파일2 <input type="file" multiple onChange={this.handleFileChange} />
                    </div>
                    <div>
                        {files.length > 0 && (
                            <div>
                                <h4>선택된 파일:</h4>
                                <ul>
                                    {files.map((file, index) => (
                                        <li key={index}>{file.name}</li>
                                    ))}
                                </ul>
                            </div>
                        )}
                    </div>
                    <div>
                        <button type="submit">제출</button>
                    </div>
                </form>
            </div>
        );
    }
}

export default CreatePost;
