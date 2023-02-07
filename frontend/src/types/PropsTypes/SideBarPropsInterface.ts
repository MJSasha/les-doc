import {Dispatch, SetStateAction} from "react";

export default interface ISideBar {
    setCurrentLessonId: Dispatch<SetStateAction<number | undefined>>,
    currentLessonId: number | undefined
}