import React, {Component, useState} from 'react';
import PropTypes from 'prop-types';

const MyStudyDetailGroupInfo = ({selectedContent}) => {


    return (
        <div className={'common-content'}>
            {selectedContent === '소개글 보기' && <p>소개글소개글</p>}

        </div>
    );
}


export default MyStudyDetailGroupInfo;