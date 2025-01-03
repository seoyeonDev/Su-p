import React, {useEffect, useState} from "react";

// localStorage 에 저장된 user_id 의 값 가져오기
export function getIdFromLocalStorage(){
    const item = localStorage.getItem("user_id");
    if (!item) return null;

    const {value, expireTime} = JSON.parse(item);
    return value;
}