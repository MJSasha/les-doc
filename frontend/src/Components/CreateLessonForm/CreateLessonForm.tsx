import React, {useState} from "react";
import LessonService from "../../services/LessonService";

import ModalWindow from "../ModalWindow/ModalWindow";

import ILesson from "../../types/LessonInterface";
import IUpdateListOfLessons from "../../types/PropsTypes/CreateLessonFormPropsInterface";

const CreateLessonForm: React.FC<IUpdateListOfLessons> = ({updateLessonsList, setVisible, visible}) => {
    const [lesson, setLesson] = useState<ILesson>({name: ""});

    const CreateHandler = () => {
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
            <div className="container d-flex flex-column justify-content-evenly h-100">
                <input className="form-control" type="text" value={lesson.name} onChange={(e) => {
                    setLesson({name: e.target.value})
                }}/>
                <button type="button" className="btn btn-primary" onClick={CreateHandler}>Create Lesson</button>
            </div>
        )
    }
    return (
        <>
            <ModalWindow getModalContent={GetModalContent} setVisible={setVisible} visible={visible}/>
        </>
    )
}

export default CreateLessonForm;