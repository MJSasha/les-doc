import React, {useEffect, useState} from "react";

import FileService from "../../services/FileService";
import {useParams} from "react-router-dom";

const FileList: React.FC = () => {
    const [fileList, setFileList] = useState<string[]>(['']);

    const {currentLessonId} = useParams();
    const id = Number(currentLessonId);
    useEffect(() => {
        console.log(id)
    }, [])

    useEffect(() => {
        getAllFileNames()
    }, [id])

    const getAllFileNames = () => {
        FileService.getAllFilesNames(id)
            .then((res) => {
                console.log(res.data)
                setFileList(res.data)
            })
            .catch(err => {
                console.log(err)
            })
    }

    return (
        <div>
            {fileList.map((file, index) => {
                return (
                    <div key={index}>
                        {file}
                    </div>
                )
            })
            }
        </div>
    )
}

export default FileList;