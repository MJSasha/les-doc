import axios from "axios";
import {url} from "../Constants/Constants";
import ILesson from "../types/LessonInterface";

const PostCreatedLesson = (lesson: ILesson): Promise<any> => {
console.log(lesson)
    return axios.post(url+'lessons', lesson, {
        headers: {
            "Content-Type": "application/json",
            Accept: 'application/json',
        },
    });
};

const CreateLessonService = {
    PostCreatedLesson
};

export default CreateLessonService;