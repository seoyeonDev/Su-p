import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import { Link } from 'react-router-dom'; 

const PostDetail = ({ postId, authorId, groupId }) => {
    const [postDetail, setPostDetail] = useState(null);
    const [fileList, setFileList] = useState([]);

    const currentUserId = localStorage.getItem('user_id'); 

    const fetchPostDetail = async () => {
        try {
            const response = await axios.get(`http://localhost:3000/studylogs/studylogsdetail/${postId}`);
            if (response.status === 200) {
                setPostDetail(response.data.vo);
                setFileList(response.data.fileList);
            }
        } catch (error) {
            console.error('Failed to fetch post details', error);
        }
    };

    useEffect(() => {
        if(postId !== null){
            fetchPostDetail();
        }
        
    }, [postId]);

    if (!postDetail) return <div>Loading...</div>;

    return (
        <div>
            <h2>{postDetail.title}</h2>

            <p>{postDetail.user_id}</p>
            <p>{postDetail.create_date}</p>
            <p>{postDetail.content}</p>

            {/* 파일 */}
            <div>
                {fileList.map((file, index) => {
                    if (file.fileType === 'KIND10') {
                        return (
                            <div key={index}>
                                <img src={file.path} alt={file.fileName} width="400" />                               
                            </div>
                        );
                    } else if (file.fileType === 'KIND20') {
                        return (
                            <div key={index}>
                                <p>파일 이름 : {file.fileName}</p>
                            </div>
                        );
                    }
                    return null;
                })}
            </div>

            {authorId === currentUserId && (
                <Link to={`/createPost?postId=${postId}&groupId=${groupId}`}>
                    <button>수정하기</button>
                </Link>
            )}
            
        </div>
    );
};

PostDetail.propTypes = {
    postId: PropTypes.string.isRequired,
    authorId: PropTypes.string.isRequired,
};

export default PostDetail;
