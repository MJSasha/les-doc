import {Dispatch, SetStateAction} from "react";

export default interface IDeleteLessonAlert {
    UpdateLessonsList: () => void,
    currentDeletingLessonId?: number | undefined,
    setVisible: Dispatch<SetStateAction<boolean>>,
    visible: boolean
}