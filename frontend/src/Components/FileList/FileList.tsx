import React, {useEffect, useState} from "react";

import FileService from "../../services/FileService";
import {useParams} from "react-router-dom";
import ModalWindow from "../ModalWindow/ModalWindow";
import FileUpload from "../Fileupload/FileUpload";

const FileList: React.FC = () => {
    const [fileList, setFileList] = useState<string[]>(['']);
    const [errMsg, setErrMsg] = useState<string>('');
    const [uploadFileFormVisibility, setUploadFileFormVisibility] = useState<boolean>(false);

    const {currentLessonId} = useParams();
    const id = Number(currentLessonId);

    useEffect(() => {
        getAllFiles()
    }, [id])

    const getAllFiles = () => {
        FileService.getAllFilesNames(id)
            .then((res) => {
                setFileList(res.data)
                setErrMsg('')
            })
            .catch(err => {
                setErrMsg('There are no files here')
                setFileList([''])
            })
    }

    return (
        <div>
            <ModalWindow getModalContent={<FileUpload updateFileList={getAllFiles} setVisible={setUploadFileFormVisibility}/>} setVisible={setUploadFileFormVisibility} visible={uploadFileFormVisibility}/>
            <button type="button" className="btn btn-primary fs-5 w-100 w-25" onClick={() => {
                setUploadFileFormVisibility(true)
            }}>Add file
            </button>
            {errMsg}
            {fileList && fileList.map((file, index) => {
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