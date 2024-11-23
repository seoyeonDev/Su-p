import React, {Component, useState} from 'react';
import PropTypes from 'prop-types';

const MyStudyDetailHome = ({selectedContent}) => {


    return (
        <div className={'common-content'}>
            {selectedContent === 'content1' && <p>content1</p>}
            {selectedContent === 'content2' && <p>content2</p>}
            {selectedContent === 'HOME' && <p>HOME</p>}

        </div>
    );
}


export default MyStudyDetailHome;