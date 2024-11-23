// Header.js
import React from 'react';

const MyStudyDetailHeader = ({title, onSelect}) => {


    return (
        <header className={'common-content'}>
            <h2>{title}</h2>
            <button onClick={() => onSelect('content1')}>Content 1</button>
            <button onClick={() => onSelect('content2')}>Content 2</button>
        </header>
    );
};

export default MyStudyDetailHeader;
