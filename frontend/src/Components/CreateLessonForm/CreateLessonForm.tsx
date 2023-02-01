import React, {useState} from "react";
import LessonService from "../../services/LessonService";

import ModalWindow from "../ModalWindow/ModalWindow";

import ILesson from "../../types/LessonInterface";
import IUpdateListOfLessons from "../../types/CreateLessonFormPropsInterface";

const CreateLessonForm: React.FC<IUpdateListOfLessons> = ({UpdateLessonList, setVisible, visible}) => {

    const [lesson, setLesson] = useState<ILesson>({name: ""});

    const CreateHandler = () => {
        LessonService.Create({
            name: lesson.name
        })
            .then(() => {
                    UpdateLessonList()
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
                <input type="text" value={lesson.name} onChange={(e) => {
                    setLesson({name: e.target.value})
                }}/>
                <button onClick={CreateHandler}>Create Lesson</button>

            </>
        )
    }
    return (
        <>
            <ModalWindow GetModalContent={GetModalContent} setVisible={setVisible} visible={visible}/>
        </>
    )
}

export default CreateLessonForm;