import {Dispatch, SetStateAction} from "react";

export default interface IUpdateListOfLessons {
    UpdateLessonList: () => void,
    setVisible: Dispatch<SetStateAction<boolean>>,
    visible: boolean
}