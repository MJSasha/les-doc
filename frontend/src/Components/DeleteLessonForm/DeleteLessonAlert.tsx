import React from "react";
import LessonService from "../../services/LessonService";
import IDeleteLessonAlert from "../../types/PropsTypes/DeleteLessonAlertPropsInterface";
import ModalWindow from "../ModalWindow/ModalWindow";

const DeleteLessonAlert: React.FC<IDeleteLessonAlert> = ({
                                                             updateLessonsList,
                                                             currentDeletingLessonId,
                                                             setVisible,
                                                             visible
                                                         }) => {


    const DeleteHandler = (id: number | undefined) => {
        LessonService.Delete(id)
            .then(() => {
                    updateLessonsList()
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
                <div className="modal-header container d-flex flex-row justify-content-between pt-2 px-3">
                    <h5 className="modal-title">Deleting lesson</h5>
                    <button onClick={() => {
                        setVisible(false)
                    }} type="button" className="btn-close"
                            aria-label="Закрыть"></button>
                </div>
                <div className="modal-body">
                    <div className="d-flex flex-column justify-content-center h-100 p-3">
                        <div className="alert alert-danger py-1 mb-1" role="alert">All files attached to this lesson
                            will be lost!
                        </div>
                    </div>
                </div>
                <div className="modal-footer">
                    <div className="pb-2 pe-3">
                        <button type="button" className="btn btn-primary"
                                onClick={() => DeleteHandler(currentDeletingLessonId)}>Delete anyway
                        </button>
                    </div>
                </div>
            </>
        )
    }

    return (
        <>
            <ModalWindow getModalContent={GetModalContent} setVisible={setVisible} visible={visible}/>
        </>
    )
}

export default DeleteLessonAlert;