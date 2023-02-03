import {Dispatch, SetStateAction} from "react";

export default interface IUpdateListOfLessons {
    UpdateLessonsList: () => void,
    setVisible: Dispatch<SetStateAction<boolean>>,
    visible: boolean
}