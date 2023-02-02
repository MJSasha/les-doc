import axios from "axios";
import {url} from "../Constants/Constants";

const GetAllLessons = (): Promise<any> => {
    return axios.get(url+'lessons');
};

const GetAllLessonsService = {
    GetAllLessons
};

export default GetAllLessonsService;