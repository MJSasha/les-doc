import {Dispatch, SetStateAction} from "react";

export default interface IUpdateListOfLessons {
    updateLessonsList: () => void,
    setVisible: Dispatch<SetStateAction<boolean>>,
    visible: boolean
}