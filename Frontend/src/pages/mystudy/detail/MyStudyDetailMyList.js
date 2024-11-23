import React, {Component, useState} from 'react';
import PropTypes from 'prop-types';

const MyStudyDetailMyList = ({selectedContent}) => {


    return (
        <div className={'common-content'}>
            {selectedContent === '내 글 보기' && <p>내글내글</p>}

        </div>
    );
}


export default MyStudyDetailMyList;