import axios from "axios";
import {url} from "../Constants/Constants";
import ILesson from "../types/LessonInterface";

const PostCreatedLesson = (lesson: ILesson): Promise<any> => {

    let formdata = new FormData();
    formdata.append("name", lesson.name);

    return axios.post(url + 'lessons', formdata);
};

const GetAllLessons = (): Promise<any> => {
    return axios.get(url + 'lessons');
};

const DeleteLesson = (id: number | undefined): Promise<any> => {
    return axios.delete(url + 'lessons/' + id)
}

const LessonService = {
    PostCreatedLesson,
    GetAllLessons,
    DeleteLesson
};

export default LessonService;