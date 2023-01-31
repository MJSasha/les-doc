import {Dispatch, SetStateAction} from "react";

export default interface IUpdateListOfLessons {
    setUpdateListOfLessons: Dispatch<SetStateAction<boolean>>,
    updateListOfLessons: boolean
}