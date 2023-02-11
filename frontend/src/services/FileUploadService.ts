import axios from "axios";
import {url} from "../Constants/Constants";

const upload = (id: number | undefined, file: File, onUploadProgress: any): Promise<any> => {
    let formData = new FormData();
    formData.append("file", file);

    return axios.post(url + 'files/uploadFile', formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
        params: {
            lessonId: id
        }
    });
};

const getAllFilesNames = (id: number | undefined): Promise<any> => {
    return axios.get(url + 'files/getAllFilesNames', {
        headers: {
            "Content-Type": "multipart/form-data",
        },
        params: {
            id
        }
    });
};

const downloadUploadedFiles = (id: number | undefined, fileName: string): Promise<any> => {
    return axios.get(url+'files/downloadFile'+fileName,{
        params: {
            id
        }
    })
}

const FileUploadService = {
    upload,
    getAllFilesNames,
    downloadUploadedFiles
};

export default FileUploadService;