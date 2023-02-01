import axios from "axios";
import {url} from "../Constants/Constants";
import ILesson from "../types/LessonInterface";

const Create = (lesson: ILesson): Promise<any> => {

    let formdata = new FormData();
    formdata.append("name", lesson.name);

    return axios.post(url + 'lessons', formdata);
};

const GetAll = (): Promise<any> => {
    return axios.get(url + 'lessons');
};

const Delete = (id: number | undefined): Promise<any> => {
    return axios.delete(url + 'lessons/' + id)
}

const LessonService = {
    Create: Create,
    GetAll: GetAll,
    Delete: Delete
};

export default LessonService;