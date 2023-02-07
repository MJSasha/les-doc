import ILesson from "../LessonInterface";
import {Dispatch, SetStateAction} from "react";

export default interface ILessonItem {
    lesson: ILesson,
    setDeleteLessonAlertVisibility: Dispatch<SetStateAction<boolean>>,
    deleteLessonAlertVisibility: boolean,
    setCurrentDeletingLessonId: Dispatch<SetStateAction<number | undefined>>,
    setCurrentLessonId: Dispatch<SetStateAction<number | undefined>>,
    currentLessonId: number | undefined
}