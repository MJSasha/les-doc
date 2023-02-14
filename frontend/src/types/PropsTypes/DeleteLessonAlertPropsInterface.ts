import {Dispatch, SetStateAction} from "react";

export default interface IDeleteLessonAlert {
    updateLessonsList: () => void,
    currentDeletingLessonId?: number | undefined,
    setVisible: Dispatch<SetStateAction<boolean>>,
    visible: boolean
}