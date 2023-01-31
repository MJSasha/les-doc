import React, {Dispatch, SetStateAction, useState} from "react";
import LessonService from "../../services/LessonService";

import ILesson from "../../types/LessonInterface";
import IUpdateListOfLessons from "../../types/CreateLessonPropsInterface";


const CreateLesson: React.FC<IUpdateListOfLessons> = ({setUpdateListOfLessons, updateListOfLessons}) => {
    const [visible, setVisible] = useState<boolean>(false);
    const [lesson, setLesson] = useState<ILesson>({name: ""});

    const PostCreatedLessonHandler = () => {
        LessonService.PostCreatedLesson({
            name: lesson.name
        })
            .then(res => {
                    setUpdateListOfLessons(!updateListOfLessons)
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
                    <button onClick={PostCreatedLessonHandler}>Create Lesson</button>
                </>
                :
                null
            }
        </>
    )
}

export default CreateLesson;