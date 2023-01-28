import axios from "axios";
import {url} from "../Constants/Constants";

const upload = (file: File, onUploadProgress: any): Promise<any> => {
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(url+'files/uploadFile', formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
    });
};

const getFiles = (exportedFileName : string) : Promise<any> => {
    return axios.get(url+'files/uploadedFiles/'+exportedFileName);//fix
};

const FileUploadService = {
    upload,
    getFiles,
};

export default FileUploadService;