import React, {useState, useEffect} from "react";
import CreateLessonService from "../../services/CreateLessonService";

import ILesson from "../../types/LessonInterface";

const CreateLesson: React.FC = () => {
    const [visible, setVisible] = useState<boolean>(false);
    const [lesson, setLesson] = useState<ILesson>({name: ""})

    const PostCreatedLesson = () => {
        CreateLessonService.PostCreatedLesson({
            name:lesson.name
        })
            .then(res => {
                    console.log(res)
                }
            )
            .catch(err => {
                    console.log(err)
                }
            )
    }
    return (
        <>
            <input type="text" value={lesson.name} onChange={(e) => {
                setLesson({name: e.target.value})
            }}/>
            <button onClick={PostCreatedLesson}>Create Lesson</button>
            {lesson.name}
        </>
    )
}

export default CreateLesson;