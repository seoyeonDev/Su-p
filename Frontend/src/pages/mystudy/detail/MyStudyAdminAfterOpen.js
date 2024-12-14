import React from 'react';

const MyStudyAdminAfterOpen = ({ selectedContent, group_id }) => {
    return (
        <div className={'common-content'}>
            {selectedContent === '스터디 관리' &&
                <div>
                    open 후
                </div>
            }

        </div>
    );
}

MyStudyAdminAfterOpen.propTypes = {

};

export default MyStudyAdminAfterOpen;