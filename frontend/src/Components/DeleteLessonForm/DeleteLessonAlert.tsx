import React from "react";
import LessonService from "../../services/LessonService";
import IDeleteLessonAlert from "../../types/PropsTypes/DeleteLessonAlertPropsInterface";
import ModalWindow from "../ModalWindow/ModalWindow";

const DeleteLessonAlert: React.FC<IDeleteLessonAlert> = ({
                                                             UpdateLessonsList,
                                                             currentDeletingLessonId,
                                                             setVisible,
                                                             visible
                                                         }) => {


    const DeleteHandler = (id: number | undefined) => {
        LessonService.Delete(id)
            .then(() => {
                    UpdateLessonsList()
                    setVisible(false)
                }
            )
            .catch(err => {
                console.log(err)
            })
    }

    const GetModalContent = () => {
        return (
            <>
                <div className="container d-flex flex-column h-100 pt-1">
                    <div className="alert alert-danger py-1" role="alert">All files attached to this lesson will be lost!</div>
                    <button type="button" className="btn btn-primary" onClick={() => DeleteHandler(currentDeletingLessonId)}>Delete anyway</button>
                </div>
            </>
        )
    }

    return (
        <>
            <ModalWindow GetModalContent={GetModalContent} setVisible={setVisible} visible={visible}/>
        </>
    )
}

export default DeleteLessonAlert;