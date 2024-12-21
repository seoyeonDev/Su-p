import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
 
const MyStudyDetailAllList = ({ selectedContent, group_id, onPostSelect  }) => {
    const [posts, setPosts] = useState([]); 

    const getStudylogs = async (group_id) => {
        try {
            const response = await axios.get('http://localhost:3000/studylogs/selectAll', {
                params: {
                    group_id: group_id,
                    user_id: null
                },
            });

            if (response.status === 200) {
                setPosts(response.data);            
            } else {
                console.error('Failed to fetch the post.');
            }
        } catch (error) {
            console.error('API call failed:', error);
        }
    };

    useEffect(() => {
        if (selectedContent === '전체글 보기') {
            getStudylogs(group_id);
        }
    }, [group_id, selectedContent]);

    return (
        <div className={'common-content'}>
            {selectedContent === '전체글 보기' &&
                <div>
                    <p> 글ID, 제목, 글쓴이, 날짜 </p>

                    {posts.length > 0 ? (
                        posts.map((item) => (
                            <div key={item.post_id}>

                                <p>
                                    {item.post_id}, 
                                    {item.user_id}, 
                                    <button onClick={() => onPostSelect(item.post_id, item.user_id)}>{item.title}</button>
                                    
                                    {item.create_date}
                                </p>
                            </div>
                        ))
                    ) : (
                        <p>게시글이 없습니다.</p>
                    )}
                </div>
            }
        </div>
    );
}

MyStudyDetailAllList.propTypes = {
    selectedContent: PropTypes.string.isRequired,
    group_id: PropTypes.string.isRequired,
};

export default MyStudyDetailAllList;