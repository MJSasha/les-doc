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

const getAll = (id: number | undefined): Promise<any> => {
    return axios.get(url + 'files/getAllFilesNames', {
        headers: {
            "Content-Type": "multipart/form-data",
        },
        params: {
            lessonId: id
        }
    });
};

const download = (id: number | undefined, fileName: string): Promise<any> => {
    return axios.get(url+'files/downloadFile/'+fileName,{
        responseType: "blob",
        params: {
            lessonId: id
        }
    })
}

const del = (id: number | undefined, fileName: string): Promise<any> => {
    return axios.delete(url+'files', {
        params: {
            fileName: fileName,
            lessonId: id
        }
    })
}

const FileService = {
    upload,
    getAll,
    download,
    del
};

export default FileService;