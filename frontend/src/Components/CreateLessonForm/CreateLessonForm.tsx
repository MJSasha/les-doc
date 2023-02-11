import React, {useState} from "react";
import LessonService from "../../services/LessonService";

import ModalWindow from "../ModalWindow/ModalWindow";

import ILesson from "../../types/LessonInterface";
import IUpdateListOfLessons from "../../types/PropsTypes/CreateLessonFormPropsInterface";

const CreateLessonForm: React.FC<IUpdateListOfLessons> = ({updateLessonsList, setVisible, visible}) => {
    const [lesson, setLesson] = useState<ILesson>({name: ""});

    const CreateHandler = (e: any) => {
        e.preventDefault()
        LessonService.Create({
            name: lesson.name
        })
            .then(() => {
                    updateLessonsList()
                    setVisible(false)
                }
            )
            .catch(err => {
                    console.log(err)
                }
            )
        setLesson({name: ""})
    }
    const GetModalContent = () => {
        return (
            <>
                <div className="modal-header container d-flex flex-row justify-content-between pt-2 px-3">
                    <h5 className="modal-title">Creating lesson</h5>
                    <button onClick={() => {
                        setVisible(false)
                    }} type="button" className="btn-close"
                            aria-label="Закрыть"></button>
                </div>
                <form onSubmit={CreateHandler}>
                    <div className="modal-body">
                        <div className="d-flex flex-column justify-content-center h-100 p-3">
                            <input autoFocus={true} className="form-control" type="text" value={lesson.name} onChange={(e) => {
                                setLesson({name: e.target.value})
                            }}/>
                        </div>
                    </div>
                    <div className="modal-footer">
                        <div className="pb-2 pe-3">
                            <button type="submit" className="btn btn-primary">Create</button>
                        </div>
                    </div>
                </form>
            </>
        )
    }
    return (
        <>
            <ModalWindow getModalContent={GetModalContent} setVisible={setVisible} visible={visible}/>
        </>
    )
}

export default CreateLessonForm;