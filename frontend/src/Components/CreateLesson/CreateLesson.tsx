import React, {Dispatch, SetStateAction, useState} from "react";
import LessonService from "../../services/LessonService";

import ILesson from "../../types/LessonInterface";
import IUpdateListOfLessons from "../../types/CreateLessonPropsInterface";


const CreateLesson: React.FC<IUpdateListOfLessons> = ({UpdateLessonList}) => {
    const [visible, setVisible] = useState<boolean>(false);
    const [lesson, setLesson] = useState<ILesson>({name: ""});

    const CreateHandler = () => {
        LessonService.Create({
            name: lesson.name
        })
            .then(res => {
                    UpdateLessonList()
                }
            )
            .catch(err => {
                    console.log(err)
                }
            )
        setLesson({name: ""})
    }
    return (
        <>
            <button onClick={() => {
                setVisible(!visible)
            }}>+
            </button>
            {visible ?
                <>
                    <input type="text" value={lesson.name} onChange={(e) => {
                        setLesson({name: e.target.value})
                    }}/>
                    <button onClick={CreateHandler}>Create Lesson</button>
                </>
                :
                null
            }
        </>
    )
}

export default CreateLesson;